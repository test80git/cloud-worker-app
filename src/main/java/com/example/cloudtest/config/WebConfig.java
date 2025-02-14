package com.example.cloudtest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/worker/new").allowedOrigins("http://localhost:8081"); // или другой URL
        registry.addMapping("/worker/new").allowedOrigins("https://app-worker.containerapps.ru");
        registry.addMapping("/worker/new").allowedOrigins("https://app-worker2.containerapps.ru");
    }
}

