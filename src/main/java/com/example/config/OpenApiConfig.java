package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI CustomOpenAPI() {
        return new OpenAPI().info(
                new Info().title("RESTful API with Java and Spring Boot").version("v1")
                        .description("Full API with crud services").termsOfService("https://github.com/hicaro4")
                        .license(new License().name("Apache 2.0").url("https://github.com/hicaro4")));
    }
}
