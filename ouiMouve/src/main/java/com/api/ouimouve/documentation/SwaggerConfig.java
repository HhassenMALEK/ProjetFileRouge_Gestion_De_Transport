package com.api.ouimouve.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OuiMouve API")
                        .version("1.0")
                        .description("Ensemble des API de OuiMouve")
                        .contact(new Contact()
                                .name("M Basier, H Malek, J Brou, S Daudey"))

                );

    }
}
