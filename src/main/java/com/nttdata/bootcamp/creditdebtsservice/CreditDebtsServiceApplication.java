package com.nttdata.bootcamp.creditdebtsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class CreditDebtsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditDebtsServiceApplication.class, args);
	}

}
