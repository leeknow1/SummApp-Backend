package com.leeknow.application.controller;

import com.leeknow.application.dto.UserLoginDTO;
import com.leeknow.application.dto.UserRegistrationDTO;
import com.leeknow.application.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public Map<String, Object> login(@RequestBody UserLoginDTO user) {
        return authenticationService.signIn(user);
    }

    @PostMapping("/sign-up")
    public Map<String, Object> login(@RequestBody UserRegistrationDTO user) {
        return authenticationService.signUp(user);
    }

    @GetMapping("/home")
    public String homePage() {
        return "home page";
    }
}
