package de.schule.informatik.lernplattform.app.scheduling;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Component
public class KubernetesLeaseLeaderElection {

    private static final Path TOKEN_PATH = Path.of("/var/run/secrets/kubernetes.io/serviceaccount/token");
    private static final int LEASE_DURATION_SECONDS = 90;

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String namespace;
    private final String holderIdentity;
    private final String leaseName;
    private final boolean enabled;

    public KubernetesLeaseLeaderElection(
            ObjectMapper objectMapper,
            @Value("${lernplattform.scheduler.leader-election.enabled:true}") boolean enabled,
            @Value("${lernplattform.scheduler.leader-election.namespace:default}") String namespace,
            @Value("${lernplattform.scheduler.leader-election.holder-identity:local}") String holderIdentity,
            @Value("${lernplattform.scheduler.leader-election.lease-name:lernplattform-scheduler}") String leaseName) {
        this.objectMapper = objectMapper;
        this.enabled = enabled;
        this.namespace = namespace;
        this.holderIdentity = holderIdentity;
        this.leaseName = leaseName;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .sslContext(defaultSslContext())
                .build();
    }

    public boolean isLeader() {
        if (!enabled) {
            return true;
        }
        if (!Files.isReadable(TOKEN_PATH)) {
            return false;
        }

        try {
            String token = Files.readString(TOKEN_PATH).trim();
            URI leaseUri = leaseUri();
            HttpResponse<String> response = send("GET", leaseUri, token, null, null);
            if (response.statusCode() == 404) {
                return createLease(token);
            }
            if (response.statusCode() != 200) {
                return false;
            }

            JsonNode lease = objectMapper.readTree(response.body());
            JsonNode metadata = lease.path("metadata");
            JsonNode spec = lease.path("spec");
            String currentHolder = spec.path("holderIdentity").asText("");
            String resourceVersion = metadata.path("resourceVersion").asText("");
            Instant renewTime = parseInstant(spec.path("renewTime").asText(null));
            int leaseDuration = spec.path("leaseDurationSeconds").asInt(LEASE_DURATION_SECONDS);
            boolean expired = renewTime == null || renewTime.plusSeconds(leaseDuration).isBefore(Instant.now());

            if (!holderIdentity.equals(currentHolder) && !expired) {
                return false;
            }
            return updateLease(token, resourceVersion);
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean createLease(String token) throws IOException, InterruptedException {
        String now = Instant.now().toString();
        String body = objectMapper.writeValueAsString(Map.of(
                "apiVersion", "coordination.k8s.io/v1",
                "kind", "Lease",
                "metadata", Map.of("name", leaseName, "namespace", namespace),
                "spec", Map.of(
                        "holderIdentity", holderIdentity,
                        "leaseDurationSeconds", LEASE_DURATION_SECONDS,
                        "acquireTime", now,
                        "renewTime", now,
                        "leaseTransitions", 0)));
        HttpResponse<String> response = send("POST", leasesCollectionUri(), token, body, "application/json");
        return response.statusCode() == 201;
    }

    private boolean updateLease(String token, String resourceVersion) throws IOException, InterruptedException {
        String now = Instant.now().toString();
        String body = objectMapper.writeValueAsString(Map.of(
                "metadata", Map.of("resourceVersion", resourceVersion),
                "spec", Map.of(
                        "holderIdentity", holderIdentity,
                        "leaseDurationSeconds", LEASE_DURATION_SECONDS,
                        "renewTime", now)));
        HttpResponse<String> response = send("PATCH", leaseUri(), token, body, "application/merge-patch+json");
        return response.statusCode() == 200;
    }

    private HttpResponse<String> send(String method, URI uri, String token, String body, String contentType)
            throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(10))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json");
        if (contentType != null) {
            builder.header("Content-Type", contentType);
        }
        if (body == null) {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        }
        return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    private URI leaseUri() {
        return URI.create("https://kubernetes.default.svc/apis/coordination.k8s.io/v1/namespaces/"
                + namespace + "/leases/" + leaseName);
    }

    private URI leasesCollectionUri() {
        return URI.create("https://kubernetes.default.svc/apis/coordination.k8s.io/v1/namespaces/"
                + namespace + "/leases");
    }

    private static Instant parseInstant(String value) {
        return value == null || value.isBlank() ? null : Instant.parse(value);
    }

    private static SSLContext defaultSslContext() {
        try {
            return SSLContext.getDefault();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot initialize SSL context", e);
        }
    }
}
