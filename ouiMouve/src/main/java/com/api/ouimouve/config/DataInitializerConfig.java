package com.api.ouimouve.config;

import com.api.ouimouve.bo.User;
import com.api.ouimouve.enumeration.Role;
import com.api.ouimouve.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class DataInitializerConfig {
    /**
     * Passage par une configuration pour initialiser les utilisateurs pour éviter les problèmes encryption
     * au démarrage de l'application. Cette méthode ne servira plus quand
     * l'application sera déployée sur un serveur de production.
     */
    @Bean
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
                user1.setEmail("marie.dupont@gmail.com");
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
    public CommandLineRunner loadSecondaryScripts(DataSource dataSource) {
        return args -> {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:sql/data/0[3-5]*.sql");

            populator.addScripts(resources);
            populator.setSeparator(";");
            populator.execute(dataSource);
            System.out.println("Scripts secondaires exécutés avec succès");
        };
    }
}
