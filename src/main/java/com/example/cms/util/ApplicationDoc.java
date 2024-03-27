package com.example.cms.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDoc {
	
	Contact contact() {
		return new Contact().name("praveen").url("http//:www.testyantra.com/").email("praveen123@gmail.com");
	}

	Info info() {
		return new Info().title("product managemt System").description("REST Full Api with basic Curd Operations")
				.version("v1").contact(contact());
	}

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}
}
