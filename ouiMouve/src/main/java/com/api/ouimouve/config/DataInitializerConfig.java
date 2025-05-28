package com.api.ouimouve.config;

import com.api.ouimouve.bo.User;
import com.api.ouimouve.enumeration.Role;
import com.api.ouimouve.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
@Slf4j
@Configuration
public class DataInitializerConfig {
    /**
     * Passage par une configuration pour initialiser les utilisateurs pour éviter les problèmes encryption
     * au démarrage de l'application. Cette méthode ne servira plus quand
     * l'application sera déployée sur un serveur de production.
     */
    @Bean
    @Order(1)
    public CommandLineRunner initializeUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setLastName("Système");
                admin.setEmail("admin@ouimouve.fr");
                admin.setPassword(passwordEncoder.encode("test")); // Mot de passe en clair ici
                admin.setRole(Role.ADMIN);
                admin.setLicenseNumber("A12345678");
                userRepository.save(admin);

                User user1 = new User();
                user1.setFirstName("Marie");
                user1.setLastName("Dupont");
                user1.setEmail("h.hassen.malek@gmail.com");
                user1.setPassword(passwordEncoder.encode("test2"));
                user1.setRole(Role.USER);
                user1.setLicenseNumber("B98765432");
                userRepository.save(user1);

                System.out.println("Utilisateurs initialisés avec succès!");
            }
        };
    }
    /**
     * Cette méthode permet d'exécuter des scripts SQL supplémentaires après l'initialisation des utilisateurs.
     * Elle est annotée avec @DependsOn pour s'assurer qu'elle s'exécute après la méthode initializeUsers.
     */
    @Bean
    @DependsOn("initializeUsers")
    @Order(2)
    public CommandLineRunner loadSecondaryScripts(DataSource dataSource) {
        return args -> {
            // Tableau explicite de tous les noms de fichiers pour garantir leur chargement
            String[] scriptFiles = {
                    "sql/data/01-Adress_Sites.sql",
                    "sql/data/03-Model_Vehicles.sql",
                    "sql/data/04-Reservation_CarPooling.sql",
                    "sql/data/05-Reparation.sql"
            };

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            for (String scriptFile : scriptFiles) {
                try {
                    Resource resource = resolver.getResource("classpath:" + scriptFile);
                    if (resource.exists()) {
                        log.info("Exécution du script : {}", scriptFile);
                        executeScriptWithErrorHandling(resource, dataSource);
                    } else {
                        log.warn("Script introuvable : {}", scriptFile);
                    }
                } catch (Exception e) {
                    log.error("Erreur lors du chargement du script {} : {}", scriptFile, e.getMessage());
                }
            }

            log.info("Traitement des scripts terminé");
        };
    }

    private void executeScriptWithErrorHandling(Resource resource, DataSource dataSource) {
        try {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(resource);
            populator.setSeparator(";");
            populator.setContinueOnError(true); // Continue même si une instruction échoue
            populator.execute(dataSource);
            log.info("Script {} exécuté avec succès", resource.getFilename());
        } catch (Exception e) {
            log.error("Erreur lors de l'exécution du script {} : {}", resource.getFilename(), e.getMessage());
        }
    }
}
