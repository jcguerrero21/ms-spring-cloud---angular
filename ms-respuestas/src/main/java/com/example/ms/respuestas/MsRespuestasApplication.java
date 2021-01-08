package com.example.ms.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class MsRespuestasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRespuestasApplication.class, args);
    }

}
