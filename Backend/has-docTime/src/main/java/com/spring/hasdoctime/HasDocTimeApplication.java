package com.spring.hasdoctime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:environment.properties")
@PropertySource("classpath:application.properties")
public class HasDocTimeApplication {


	public static void main(String[] args) {
		SpringApplication.run(HasDocTimeApplication.class, args);
	}




}

