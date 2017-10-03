package com.example.relay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RelayApplication {
	private static final String PROPERTIES = "spring.config.location=classpath:/google.properties";
	public static void main(String[] args) {
		new SpringApplicationBuilder(RelayApplication.class).properties(PROPERTIES).run(args);
	}
}
