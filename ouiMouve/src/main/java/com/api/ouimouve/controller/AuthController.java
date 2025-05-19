package com.api.ouimouve.controller;

import com.api.ouimouve.bo.User;
import com.api.ouimouve.dto.AuthResponse;
import com.api.ouimouve.dto.LoginRequest;
import com.api.ouimouve.dto.RegisterRequest;
import com.api.ouimouve.enumeration.Role;
import com.api.ouimouve.exception.UserException;
import com.api.ouimouve.exception.ValidationErrorResponse;
import com.api.ouimouve.repository.UserRepository;
import com.api.ouimouve.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * AuthController is a REST controller that handles authentication-related operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Handles user login.
     * @param request the login request containing email and password
     * @return a ResponseEntity containing the authentication token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                )
        );

        return ResponseEntity.ok(new AuthResponse(token));
    }
    /**
     * Handles user registration.
     * @param request the registration request containing user details
     * @return a ResponseEntity containing the authentication token or validation errors
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        log.info("Tentative d'inscription pour l'utilisateur: {}", request.getEmail());

        // Créer un objet de validation pour collecter les erreurs
        ValidationErrorResponse validationErrors = new ValidationErrorResponse();
        validationErrors.setStatus(HttpStatus.CONFLICT.value());

        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Email déjà utilisé: {}", request.getEmail());
            validationErrors.addFieldError("email",
                    "Un utilisateur avec l'adresse email " + request.getEmail() + " existe déjà");
        }

        // Vérifier si le numéro de licence existe déjà
        if (request.getLicenseNumber() != null &&
                userRepository.findByLicenseNumber(request.getLicenseNumber()).isPresent()) {
            log.warn("Numéro de licence déjà utilisé: {}", request.getLicenseNumber());
            validationErrors.addFieldError("licenseNumber",
                    "Un utilisateur avec le numéro de licence " + request.getLicenseNumber() + " existe déjà");
        }

        // Si des erreurs ont été trouvées, retourner la réponse avec les erreurs
        if (validationErrors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationErrors);
        }

        // Procéder à l'inscription si aucune erreur n'a été trouvée
        User user = new User();
        try {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.USER);
            user.setLicenseNumber(request.getLicenseNumber());

            userRepository.save(user);
            log.info("Utilisateur inscrit avec succès: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Erreur inattendue lors de l'inscription: {}", e.getMessage(), e);
            throw new UserException("Erreur lors de la création du compte utilisateur");
        }

        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                )
        );

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

