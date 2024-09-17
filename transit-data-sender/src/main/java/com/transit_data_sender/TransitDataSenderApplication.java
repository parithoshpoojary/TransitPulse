package com.transit_data_sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransitDataSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransitDataSenderApplication.class, args);
	}

}
