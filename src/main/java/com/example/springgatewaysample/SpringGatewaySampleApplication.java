package com.example.springgatewaysample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class SpringGatewaySampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGatewaySampleApplication.class, args);
        Hooks.enableAutomaticContextPropagation();
    }

}
