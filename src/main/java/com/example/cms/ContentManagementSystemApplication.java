package com.example.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.cms.controller.UserController;

@SpringBootApplication
@EnableScheduling
public class ContentManagementSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ContentManagementSystemApplication.class, args);
		System.out.println("Application Has Started");
	}
}
