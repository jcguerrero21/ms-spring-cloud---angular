package com.example.msusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.example.commons.usuarios.entity"})
public class MsUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }

}
