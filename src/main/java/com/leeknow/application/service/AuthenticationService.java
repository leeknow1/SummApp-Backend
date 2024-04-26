package com.leeknow.application.service;

import com.leeknow.application.configuration.CustomUserDetails;
import com.leeknow.application.configuration.JWT.JwtService;
import com.leeknow.application.dto.UserLoginDTO;
import com.leeknow.application.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public Map<String, Object> signIn(UserLoginDTO dto) {
        Map<String, Object> response = new HashMap<>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        User user = userService.findByEmail(dto.getEmail());
        String token = jwtService.generateToken(new CustomUserDetails(user));

        response.put("token", token);

        return response;
    }
}
