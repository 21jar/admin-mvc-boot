package com.ixiangliu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@SpringBootApplication
public class AdminMvcBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminMvcBootApplication.class, args);
    }

}
