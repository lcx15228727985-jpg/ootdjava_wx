package com.fitting.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fitting.app.mapper")
public class FittingApplication {
    public static void main(String[] args) {
        SpringApplication.run(FittingApplication.class, args);
    }
}