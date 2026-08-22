package de.schule.informatik.lernplattform.app.registration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class SmtpEmailVerificationSender implements EmailVerificationSender {

    private final JavaMailSender mailSender;
    private final String from;
    private final String publicBaseUrl;

    public SmtpEmailVerificationSender(
            JavaMailSender mailSender,
            @Value("${lernplattform.registration.mail.from:no-reply@localhost}") String from,
            @Value("${lernplattform.public-base-url:http://localhost:4200}") String publicBaseUrl) {
        this.mailSender = mailSender;
        this.from = from;
        this.publicBaseUrl = publicBaseUrl;
    }

    @Override
    public void sendVerification(String recipient, String schoolName, String rawToken) {
        String link = publicBaseUrl + "/registrierung-bestaetigen?token="
                + URLEncoder.encode(rawToken, StandardCharsets.UTF_8);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipient);
        message.setSubject("E-Mail-Adresse für Schulregistrierung bestätigen");
        message.setText("Für die Registrierung von \"" + schoolName + "\" wurde diese E-Mail-Adresse angegeben.\n\n"
                + "Bitte bestätige die Adresse innerhalb von 24 Stunden über folgenden Link:\n" + link
                + "\n\nWenn du keinen Antrag gestellt hast, kannst du diese Nachricht ignorieren.");
        mailSender.send(message);
    }
}
