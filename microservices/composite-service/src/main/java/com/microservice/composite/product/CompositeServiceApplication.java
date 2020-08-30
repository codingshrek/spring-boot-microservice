package com.microservice.composite.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("com.microservice")
public class CompositeServiceApplication {


	@Bean
	RestTemplate restTemplate(){
		return  new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(CompositeServiceApplication.class, args);
	}

}
