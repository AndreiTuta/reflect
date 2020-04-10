package com.andreituta;

import org.springframework.context.annotation.PropertySource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@PropertySource("classpath:application.properties")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}	
}