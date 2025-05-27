package com.api.ouimouve.controller;

import com.api.ouimouve.dto.EmailRequest;
import com.api.ouimouve.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Contrôleur REST pour l'envoi d'emails personnalisés.
 */
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    /**
     * Endpoint POST pour envoyer un email.
     *
     * @param request le corps de la requête JSON contenant to, subject, body
     * @return réponse JSON confirmant l'envoi ou une erreur
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest request) {
        try {
            emailService.sendAlert(
                    request.getTo(),
                    request.getSubject(),
                    request.getBody()
            );

            return ResponseEntity.ok(Map.of("message", "Email envoyé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Erreur lors de l'envoi de l'email : " + e.getMessage()));
        }
    }
}
