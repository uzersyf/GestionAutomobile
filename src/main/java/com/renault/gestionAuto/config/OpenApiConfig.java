package com.renault.gestionAuto.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gestionAutoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion Auto – Garages, Véhicules, Accessoires")
                        .description("Microservice Renault: gestion des garages, véhicules, accessoires, recherches et événements Kafka")
                        .version("v1")
                        .contact(new Contact().name("Renault").email("support@example.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation projet")
                        .url("https://example.com/docs"));
    }

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("gestion-auto")
                .packagesToScan("com.renault.gestionAuto.api")
                .build();
    }
}
