package io.com.github.matheusfy.api.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocsConfiguration {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().
            components(
                (new Components().addSecuritySchemes(
                "bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            )
            .info(new Info()
                .title("Voll.med API")
                .description("Api da voll-med contendo funionalidades de CRUD para medicos, pacientes, agendamento de consultas e cancelamentos")
                .contact(new Contact()
                    .name("Backend Team")
                    .email("backend-team@voll.med"))
                .version("v1")
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://voll.med/api/licenca")))
            ;
    }
}
