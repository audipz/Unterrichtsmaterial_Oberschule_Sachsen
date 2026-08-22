package de.schule.informatik.lernplattform.app.registration;

public interface EmailVerificationSender {
    void sendVerification(String recipient, String schoolName, String rawToken);
}
