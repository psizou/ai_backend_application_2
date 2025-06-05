package com.jsonplaceholder.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("JSON Placeholder API")
                .description(
                    "A Spring Boot application that provides a RESTful API for managing users and their data")
                .version("1.0.0")
                .contact(
                    new Contact()
                        .name("Your Name")
                        .email("your.email@example.com")
                        .url("https://github.com/yourusername"))
                .license(
                    new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
        .servers(
            List.of(
                new Server().url("http://localhost:8080").description("Local Development Server")));
  }
}
