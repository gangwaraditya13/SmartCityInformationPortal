package com.smart.city.SmartCityInformationPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.smart.city.SmartCityInformationPortal.repository")
public class SmartCityInformationPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCityInformationPortalApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager PTM(MongoDatabaseFactory factoryDB){
		return new MongoTransactionManager(factoryDB);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
