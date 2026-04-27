package com.lhpdesenvolvimentos.jobfast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JobfastApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobfastApplication.class, args);
	}

}
