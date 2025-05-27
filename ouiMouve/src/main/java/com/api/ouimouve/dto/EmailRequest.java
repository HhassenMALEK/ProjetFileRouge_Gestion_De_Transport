package com.api.ouimouve.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequest {

    @NotBlank(message = "L'adresse email du destinataire est obligatoire")
    @Email(message = "Format d'email invalide")
    private String to;

    @NotBlank(message = "Le sujet de l'email est obligatoire")
    private String subject;

    @NotBlank(message = "Le contenu de l'email est obligatoire")
    private String body;

}
