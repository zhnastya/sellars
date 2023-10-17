package com.example.sellars.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Open API Documentation",
                description = "Loyalty System", version = "1.0.0",
                contact = @Contact(
                        name = "Zharkova Anastasia",
                        email = "zharkovaanasta@yandex.ru"
                )
        )
)
public class OpenApiConfig {

}
