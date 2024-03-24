package com.leeknow.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/test")
    public String testPage() {
        return "test page";
    }

    @PostMapping("/login")
    public String login() {
        return "login page";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home page";
    }
}
