package com.example.ms.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.example.ms.respuestas.model.entity",
        "com.example.commons.examenes.model.entity"})
public class MsRespuestasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRespuestasApplication.class, args);
    }

}
