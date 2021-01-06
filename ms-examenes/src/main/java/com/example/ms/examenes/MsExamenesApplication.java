package com.example.ms.examenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.example.commons.examenes.model.entity"})
public class MsExamenesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsExamenesApplication.class, args);
    }

}
