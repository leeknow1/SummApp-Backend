package com.leeknow.summapp.service;

import com.leeknow.summapp.configuration.CustomUserDetails;
import com.leeknow.summapp.configuration.JWT.JwtService;
import com.leeknow.summapp.configuration.expections.UserAlreadyExistException;
import com.leeknow.summapp.dto.UserLoginDTO;
import com.leeknow.summapp.dto.UserRegistrationDTO;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.EventType;
import com.leeknow.summapp.enums.Role;
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
    private final EventService eventService;

    public Map<String, Object> signIn(UserLoginDTO dto) {
        Map<String, Object> response = new HashMap<>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        User user = userService.findByEmail(dto.getEmail());
        String token = jwtService.generateToken(new CustomUserDetails(user));
        jwtService.generateRefreshToken(user);

        response.put("token", token);
        eventService.create(EventType.SIGNING_IN.getId(), user);
        return response;
    }

    public Map<String, Object> signUp(UserRegistrationDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if (userService.findByEmail(dto.getEmail()) != null)
            throw new UserAlreadyExistException("Пользователь с такой почтой уже существует!");

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName() == null ? "" : dto.getMiddleName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoleId(Role.USER.getRoleId());

        user = userService.save(user).get("user");
        String token = jwtService.generateToken(new CustomUserDetails(user));
        jwtService.generateRefreshToken(user);

        response.put("token", token);
        eventService.create(EventType.NEW_USER.getId(), user);
        return response;
    }
}
