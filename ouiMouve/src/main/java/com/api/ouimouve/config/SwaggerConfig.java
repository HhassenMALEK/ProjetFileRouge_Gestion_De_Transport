package com.api.ouimouve.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig is a configuration class that sets up Swagger for the application.
 */
@Configuration
public class SwaggerConfig {

    @Configuration
    public static class SwaggerCustomConfig {
        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .components(new Components()
                            .addSecuritySchemes("bearerAuth",
                                    new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")
                            )
                    )
                    .info(new Info()
                            .title("OuiMouve API")
                            .version("1.0")
                            .description("Ensemble des API de OuiMouve")
                            .contact(new Contact()
                                    .name("M Basier, H Malek, J Brou, S Daudey"))

                    )
                    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));


        }
    }
}