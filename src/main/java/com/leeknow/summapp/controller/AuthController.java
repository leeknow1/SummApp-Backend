package com.leeknow.summapp.controller;

import com.leeknow.summapp.dto.UserLoginDTO;
import com.leeknow.summapp.dto.UserRegistrationDTO;
import com.leeknow.summapp.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserLoginDTO user) {
        Map<String, Object> result = authenticationService.signIn(user);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRegistrationDTO user) {
        Map<String, Object> result = authenticationService.signUp(user);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/refresh-token")
    public String refreshToken() {
        return "refresh token";
    }
}
