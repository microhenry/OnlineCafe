package com.group2.cafejava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.group2.cafejava.mapper")
public class CafeJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeJavaApplication.class, args);
    }

}
