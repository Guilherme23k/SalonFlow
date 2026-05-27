package com.salonflow.backend.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("SalonFlow API")
                        .version("1.0.0")
                        .description("Internal API of SalonFlow - Spring Boot"));
    }

    @Bean
    public OperationCustomizer globalHandlers(){
        return (operation, handlerMethod) -> {
            operation.addParametersItem(
                    new Parameter()
                            .in(ParameterIn.HEADER.toString())
                            .name("X-Internal-Api-Key")
                            .description("Chave de autenticação interna")
                            .required(true)
            );
            operation.addParametersItem(
                    new Parameter()
                            .in(ParameterIn.HEADER.toString())
                            .name("X-Tenant-Id")
                            .description("UUID do tenant")
                            .required(true)
            );
            return operation;
        };
    }

}
