package br.com.wk.donors.util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    /**
     * Swagger UI: http://localhost:8080/swagger-ui/index.html
     * JSON OpenAPI: http://localhost:8080/v3/api-docs
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API WK Doadores")
                        .version("1.0")
                        .description("Documentação da API WK Doadores."));
    }
}
