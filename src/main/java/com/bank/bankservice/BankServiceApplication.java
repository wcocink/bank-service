package com.bank.bankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class BankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServiceApplication.class, args);
	}



}

