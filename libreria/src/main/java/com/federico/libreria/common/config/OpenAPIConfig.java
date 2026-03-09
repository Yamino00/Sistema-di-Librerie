package com.federico.libreria.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EFTI Gate Adapter")
                        .description("Documentazione API per il servizio EFTI Gate Adapter")
                        .version("1.0") // Campo obbligatorio per wso2
                );
    }
}
