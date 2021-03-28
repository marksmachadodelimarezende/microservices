package com.microservice.marks.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CrudApplication {

	Logger logger = LoggerFactory.getLogger(CrudApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
//		logger.trace("A TRACE Message"); //Nao exibe no logger do intellij. Padrao INFO, WARN e ERROR apenas
//		logger.debug("A DEBUG Message"); //Nao exibe no logger do intellij. Padrao INFO, WARN e ERROR apenas
		logger.info("An INFO Message");
		logger.warn("A WARN Message");
		logger.error("An ERROR Message");
		return args -> {
			System.out.println("*** MICROSERVICE CRUD STATED ***");
		};
	}

}
