package com.sogeti.filmLand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@ComponentScan(basePackages = "com.sogeti.*")
public class filmLandSogetiRunner    {

	public static void main(String[] args) {
		SpringApplication.run(filmLandSogetiRunner.class, args);
	}
}
