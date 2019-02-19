package com.zone.quartz_module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zone.quartz_module.mapper")
public class QuartzModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzModuleApplication.class, args);
    }
}



