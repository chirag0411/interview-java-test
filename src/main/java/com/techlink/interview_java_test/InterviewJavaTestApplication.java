package com.techlink.interview_java_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class InterviewJavaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewJavaTestApplication.class, args);
	}

}
