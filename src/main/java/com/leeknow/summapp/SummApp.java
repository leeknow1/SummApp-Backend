package com.leeknow.summapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableCaching
@SpringBootApplication
public class SummApp {

    public static void main(String[] args) {
        SpringApplication.run(SummApp.class, args);
    }

}
