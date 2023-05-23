package com.spring.hasdocTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HasDocTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HasDocTimeApplication.class, args);
	}
}

