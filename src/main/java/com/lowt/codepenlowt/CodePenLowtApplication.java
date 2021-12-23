package com.lowt.codepenlowt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableCaching
@MapperScan("com.lowt.codepenlowt.mapper")
@SpringBootApplication
@EnableScheduling
public class CodePenLowtApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodePenLowtApplication.class, args);
    }
}