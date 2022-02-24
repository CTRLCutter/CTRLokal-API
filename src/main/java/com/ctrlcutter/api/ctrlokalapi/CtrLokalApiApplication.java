package com.ctrlcutter.api.ctrlokalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ctrlcutter.backend.*", "com.ctrlcutter.api.ctrlokalapi.*"})
public class CtrLokalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CtrLokalApiApplication.class, args);
    }
}
