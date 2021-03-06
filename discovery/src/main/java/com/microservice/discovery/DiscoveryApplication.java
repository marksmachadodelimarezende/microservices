package com.microservice.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplication {
	private static final Logger log = LoggerFactory.getLogger(DiscoveryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryApplication.class, args);
		logRunnerInitial();
	}

	private static void logRunnerInitial() {
		log.info("MICROSERVICE DISCOVERY (Service Registry) IS RUNNING");
	}

}
