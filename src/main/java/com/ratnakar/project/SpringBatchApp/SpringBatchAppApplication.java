package com.ratnakar.project.SpringBatchApp;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({
		"com.ratnakar.project.SpringBatchApp.configuration",
		"com.ratnakar.project.SpringBatchApp.service",
		"com.ratnakar.project.SpringBatchApp.listener",
		"com.ratnakar.project.SpringBatchApp.reader",
		"com.ratnakar.project.SpringBatchApp.processor",
		"com.ratnakar.project.SpringBatchApp.writer",
		"com.ratnakar.project.SpringBatchApp.controller"
})
@EnableAsync
//@EnableScheduling
public class SpringBatchAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchAppApplication.class, args);

	}
}
