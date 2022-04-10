package com.ctrlcutter.api.ctrlokalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ctrlcutter.*")
@EnableJpaRepositories("com.ctrlcutter.*")
@ComponentScan(basePackages = {"com.ctrlcutter.backend.*", "com.ctrlcutter.api.ctrlokalapi.*"})
public class CtrLokalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CtrLokalApiApplication.class, args);
    }
}
