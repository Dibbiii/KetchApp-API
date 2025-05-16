package com.alessandra_alessandro.ketchapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class KetchappApplication {

    public static void main(String[] args) {
        SpringApplication.run(KetchappApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Consente tutte le rotte
                        .allowedOrigins("http://localhost:56501") // Sostituisci con il dominio del front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Metodi consentiti
                        .allowedHeaders("*") // Consente tutti gli header
                        .allowCredentials(true); // Consente l'invio di cookie
            }
        };
    }
}