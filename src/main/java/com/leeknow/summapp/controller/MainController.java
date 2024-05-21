package com.leeknow.summapp.controller;

import com.leeknow.summapp.dto.UserLoginDTO;
import com.leeknow.summapp.dto.UserRegistrationDTO;
import com.leeknow.summapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public Map<String, Object> signIn(@RequestBody UserLoginDTO user) {
        return authenticationService.signIn(user);
    }

    @PostMapping("/sign-up")
    public Map<String, Object> signUp(@RequestBody UserRegistrationDTO user) {
        return authenticationService.signUp(user);
    }

    @GetMapping("/home")
    public String homePage() {
        return "home page";
    }
}
