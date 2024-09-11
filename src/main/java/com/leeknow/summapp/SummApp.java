package com.leeknow.summapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SummApp {

    public static void main(String[] args) {
        SpringApplication.run(SummApp.class, args);
    }

}
