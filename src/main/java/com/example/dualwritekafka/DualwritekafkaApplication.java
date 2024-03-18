package com.example.dualwritekafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DualwritekafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DualwritekafkaApplication.class, args);
	}

}
