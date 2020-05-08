package com.at.reflect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EntityScan("com.at.reflect.model.entity")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}	
}