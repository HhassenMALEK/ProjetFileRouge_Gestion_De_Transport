package com.api.ouimouve.controller;

import com.api.ouimouve.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendAlert(request.getTo(), request.getSubject(), request.getBody());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Email envoyé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de l'envoi de l'email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> testEmail() {
        try {
            emailService.sendAlert(
                    "h_hassen.malek@hotmail.com",
                    "Test d'envoi d'email",
                    "Ceci est un email de test envoyé depuis l'API OuiMouve."
            );

            Map<String, String> response = new HashMap<>();
            response.put("message", "Email de test envoyé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de l'envoi de l'email de test: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

class EmailRequest {
    private String to;
    private String subject;
    private String body;

    // Getters et setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}