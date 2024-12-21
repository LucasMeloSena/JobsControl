package br.dev.lucasena.jobs_control.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(new Info()
        .title("Gestão de Vagas")
        .description("Jobs control API")
        .version("1"))
        .schemaRequirement("jwt_auth", createSecurityScheme());
  }
  
  private SecurityScheme createSecurityScheme() {
    return new SecurityScheme()
    .name("jwt_auth")
    .scheme("bearer")
    .type(SecurityScheme.Type.HTTP)
    .in(SecurityScheme.In.HEADER)
        .bearerFormat("JWT");
  }
}
