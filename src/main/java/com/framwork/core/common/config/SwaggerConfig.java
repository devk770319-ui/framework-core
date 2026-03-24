package com.framwork.core.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI frameworkCoreOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Framework Core API")
                        .description("Framework Core API documentation")
                        .version("v1"));
    }
}
