package com.cryptocurrency.data.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The CorsConfig class is a Spring configuration class for configuring CORS (Cross-Origin Resource Sharing).
 * Author: Mouhamadou Ahibou DIALLO
 */
@Configuration
public class CorsConfig {

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     *
     * @return a WebMvcConfigurer that provides CORS mapping configurations
     *         allowing all paths ("/**") for the origin "<a href="http://localhost:8080">MyApp</a>"
     *         with allowed HTTP methods GET, POST, PUT, DELETE, all headers,
     *         and allowing credentials.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Adds CORS (Cross-Origin Resource Sharing) mappings to the given registry.
             * <p>
             * The mapping provided allows all paths ("/**") for the origin "<a href="http://localhost:8080">MyApp</a>"
             * with allowed HTTP methods GET, POST, PUT, DELETE, all headers, and allowing credentials.
             * </p>
             *
             * @param registry the registry to add CORS mappings to
             */
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
