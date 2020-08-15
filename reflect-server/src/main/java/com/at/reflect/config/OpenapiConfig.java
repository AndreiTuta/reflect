package com.at.reflect.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

//@formatter:off
@OpenAPIDefinition(info = @Info(
                    title="Reflect API",
                    version = "1.0.0",
                    contact = @Contact(
                        name = "Andrei Tuta",
                        email = "reflect.romania@gmail.com" 
                    ))
                  )
//@formatter:on
@Configuration
public class OpenapiConfig {}
