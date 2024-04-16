package com.leeknow.application.controller;

import com.leeknow.application.dto.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO user) {
        return "login page";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home page";
    }
}
