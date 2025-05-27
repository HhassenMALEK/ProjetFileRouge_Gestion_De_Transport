package com.api.ouimouve.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service responsable de l'envoi d'emails texte simples.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    /**
     * Envoie un email simple au format texte.
     *
     * @param to destinataire
     * @param subject sujet de l'email
     * @param body contenu de l'email
     */
    public void sendAlert(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        logger.info("✅ Email envoyé à {}", to);
    }

    /**
     * Exemple d'appel interne pour tester l'envoi.
     */
    public void uneFonctionQuelconque() {
        sendAlert(
                "h_hassen.malek@hotmail.com",
                "Sujet de l'email",
                "Contenu de l'email d'alerte"
        );
    }
}
