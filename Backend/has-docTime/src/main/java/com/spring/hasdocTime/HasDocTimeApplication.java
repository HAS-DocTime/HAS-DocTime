package com.spring.hasdocTime;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
	@PropertySource("classpath:environment.properties"),
	@PropertySource("classpath:application.properties")
})
public class HasDocTimeApplication {


	public static void main(String[] args) {
		SpringApplication.run(HasDocTimeApplication.class, args);
	}

}

