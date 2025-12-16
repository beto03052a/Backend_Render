package com.example.backendas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * SwaggerConfig
 * -----------------------------------------------------
 * ✔ Habilita documentación interactiva de la API con Swagger UI (OpenAPI 3)
 * ✔ Añade soporte para autenticación JWT tipo Bearer
 * ✔ URL de acceso: http://localhost:9191/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestión de Condominios API")
                        .version("1.0.0")
                        .description("API REST para gestión de condominios, residentes, unidades, pagos, reservas e incidencias."))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth", List.of("read", "write"))
                );
    }
}
