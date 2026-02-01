package com.smart.city.SmartCityInformationPortal.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CityHub – Smart City Information Portal API")
                        .version("1.0.0")
                        .description(
                                "CityHub is a centralized smart city platform providing APIs for " +
                                        "citizen complaints, city services, administration, and public utilities."
                        )
                        .contact(new Contact()
                                .name("Aditya Kumar")
                                .email("gangwaraditya13@gmail.com")
                                .url("https://github.com/gangwaraditya13"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080/cityhub").description("Local Server"),
                        new Server().url("https://smartcityinformationportal.onrender.com/cityhub")
                                .description("Production Server")
                ));
    }
}
