package com.framwork.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {"com.framwork.core", "com.biz"})
@ConfigurationPropertiesScan(basePackages = "com.framwork.core")
public class FrameworkCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrameworkCoreApplication.class, args);
    }
}
