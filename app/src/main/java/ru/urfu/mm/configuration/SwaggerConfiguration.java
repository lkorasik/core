package ru.urfu.mm.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("JWT Authentication"))
                .components(new Components().addSecuritySchemes("JWT Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("Автоматизация магистратуры")
                        .description("API системы автоматизации магистратуры")
                        .version("1.3-SNAPSHOT")
                        .contact(new Contact()
                                .name("Lev (lkorasik83@gmail.com)")
                                .email("lkorasik83@.gmail.com")
                                .url("")
                        )
                );
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
