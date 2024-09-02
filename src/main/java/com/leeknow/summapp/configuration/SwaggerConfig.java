package com.leeknow.summapp.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Value("${application.title}")
    private String applicationTitle;

    @Value("${application.version}")
    private String applicationVersion;

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info(new Info().title(applicationTitle).version(applicationVersion));
    }
}
