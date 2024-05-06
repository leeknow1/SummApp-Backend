package com.leeknow.application.service;

import com.leeknow.application.configuration.CustomUserDetails;
import com.leeknow.application.configuration.JWT.JwtService;
import com.leeknow.application.dto.UserLoginDTO;
import com.leeknow.application.dto.UserRegistrationDTO;
import com.leeknow.application.entity.User;
import com.leeknow.application.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public Map<String, Object> signIn(UserLoginDTO dto) {
        Map<String, Object> response = new HashMap<>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        User user = userService.findByEmail(dto.getEmail());
        String token = jwtService.generateToken(new CustomUserDetails(user));

        response.put("token", token);

        return response;
    }

    public Map<String, Object> signUp(UserRegistrationDTO dto) {
        Map<String, Object> response = new HashMap<>();

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoleId(Role.USER.getRoleId());

        user = userService.save(user);
        String token = jwtService.generateToken(new CustomUserDetails(user));

        response.put("token", token);

        return response;
    }
}