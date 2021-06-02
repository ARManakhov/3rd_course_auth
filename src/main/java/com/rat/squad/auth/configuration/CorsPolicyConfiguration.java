package com.rat.squad.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cors policy configuration file
 * completely disables cors in this project
 */
@Configuration
public class CorsPolicyConfiguration {

    /**
     * mehod that creates WebMvcConfigurer bean with configuration, that allows use all endpoints without cors
     * @return WebMvcConfigurer with needed cors mapping
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }
}