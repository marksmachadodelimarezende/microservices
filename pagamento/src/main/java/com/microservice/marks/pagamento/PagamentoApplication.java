package com.microservice.marks.pagamento;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.microservice")
@EnableDiscoveryClient
public class PagamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagamentoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
            System.out.println("MICROSERVICE PAGAMENTO ONLINE");
        };
    }

}
