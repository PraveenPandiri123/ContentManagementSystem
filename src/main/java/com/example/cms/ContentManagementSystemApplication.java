package com.example.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.cms.controller.UserController;

@SpringBootApplication
public class ContentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentManagementSystemApplication.class, args);	
		System.out.println("Application Has Started");
	}
}
