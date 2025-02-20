package com.Project.email_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.Project.email_generator")

public class EmailGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailGeneratorApplication.class, args);
	}

}
