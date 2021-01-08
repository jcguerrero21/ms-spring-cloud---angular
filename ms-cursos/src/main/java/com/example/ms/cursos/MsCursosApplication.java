package com.example.ms.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.example.ms.cursos.model.entity",
        "com.example.commons.examenes.model.entity"})
public class MsCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCursosApplication.class, args);
    }

}
