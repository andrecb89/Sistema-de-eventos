package com.demo.Sistema.de.Eventos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(
                new Info()
                        .title("API for Event Management")
                        .description("API for event creation, modification, querying, and deletion.")
                        .version("1.0.0")
        );
    }
}