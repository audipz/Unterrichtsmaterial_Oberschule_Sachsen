package de.schule.informatik.lernplattform.app.scheduling;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Component
public class KubernetesLeaseLeaderElection {

    private static final Path SERVICE_ACCOUNT_DIR = Path.of("/var/run/secrets/kubernetes.io/serviceaccount");
    private static final Path TOKEN_PATH = SERVICE_ACCOUNT_DIR.resolve("token");
    private static final Path CA_PATH = SERVICE_ACCOUNT_DIR.resolve("ca.crt");
    private static final int LEASE_DURATION_SECONDS = 90;

    private final ObjectMapper objectMapper;
    private final String namespace;
    private final String holderIdentity;
    private final String leaseName;
    private final boolean enabled;
    private volatile boolean leader;

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
        this.leader = !enabled;
    }

    @Scheduled(fixedDelayString = "${lernplattform.scheduler.leader-election.renew-delay:PT30S}")
    public void renewLeadership() {
        if (!enabled) {
            leader = true;
            return;
        }
        leader = tryAcquireOrRenew();
    }

    public boolean isLeader() {
        return leader;
    }

    private boolean tryAcquireOrRenew() {
        if (!Files.isReadable(TOKEN_PATH) || !Files.isReadable(CA_PATH)) {
            return false;
        }

        try {
            String token = Files.readString(TOKEN_PATH).trim();
            HttpResponse<String> response = send("GET", leaseUri(), token, null, null);
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
        String body = objectMapper.writeValueAsString(Map.of(
                "metadata", Map.of("resourceVersion", resourceVersion),
                "spec", Map.of(
                        "holderIdentity", holderIdentity,
                        "leaseDurationSeconds", LEASE_DURATION_SECONDS,
                        "renewTime", Instant.now().toString())));
        HttpResponse<String> response = send("PATCH", leaseUri(), token, body, "application/merge-patch+json");
        return response.statusCode() == 200;
    }

    private HttpResponse<String> send(String method, URI uri, String token, String body, String contentType)
            throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .sslContext(kubernetesSslContext())
                .build();

        HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(10))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json");
        if (contentType != null) {
            builder.header("Content-Type", contentType);
        }
        builder.method(method, body == null
                ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(body));
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

    private static SSLContext kubernetesSslContext() {
        try (InputStream caInput = Files.newInputStream(CA_PATH)) {
            var certificate = CertificateFactory.getInstance("X.509").generateCertificate(caInput);
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            trustStore.setCertificateEntry("kubernetes-ca", certificate);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, trustManagerFactory.getTrustManagers(), null);
            return context;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot initialize Kubernetes SSL context", e);
        }
    }
}
