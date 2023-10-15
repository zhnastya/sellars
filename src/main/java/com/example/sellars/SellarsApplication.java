package com.example.sellars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SellarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellarsApplication.class, args);
	}

}
