package com.at.reflect;

import org.springframework.context.annotation.PropertySource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@PropertySource("classpath:application.properties")
@EntityScan("com.at.reflect.model.entities")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}	
}