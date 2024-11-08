package com.leeknow.summapp.user.controller;

import com.leeknow.summapp.user.dto.UserLoginDTO;
import com.leeknow.summapp.user.dto.UserRegistrationDTO;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.user.service.AuthenticationService;
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
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRegistrationDTO user,
                                    @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        Map<String, Object> result = authenticationService.signUp(user, Language.getLanguageById(lang));
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Integer id,
                                          @RequestBody String code,
                                          @RequestHeader(name = "Accept-Language", defaultValue = "1") String lang) {
        Map<String, Object> result = authenticationService.activate(id, code, Language.getLanguageById(lang));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/refresh-token")
    public String refreshToken() {
        return "refresh token";
    }
}
