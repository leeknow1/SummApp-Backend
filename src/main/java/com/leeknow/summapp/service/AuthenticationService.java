package com.leeknow.summapp.service;

import com.leeknow.summapp.configuration.JWT.JwtService;
import com.leeknow.summapp.configuration.expections.UserAlreadyExistException;
import com.leeknow.summapp.constant.AuthMessageConstant;
import com.leeknow.summapp.dto.UserLoginDTO;
import com.leeknow.summapp.dto.UserRegistrationDTO;
import com.leeknow.summapp.entity.Role;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.EventType;
import com.leeknow.summapp.enums.Language;
import com.leeknow.summapp.enums.RoleEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EventService eventService;
    private final EmailService emailService;

    public Map<String, Object> signIn(UserLoginDTO dto) {
        Map<String, Object> response = new HashMap<>();

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        User user = ((User) authenticate.getCredentials());
        String token = jwtService.generateToken(user);
        jwtService.generateRefreshToken(user);

        response.put("token", token);
        eventService.create(EventType.SIGNING_IN.getId(), user);
        return response;
    }

    public Map<String, Object> signUp(UserRegistrationDTO dto, Language lang) {
        Map<String, Object> response = new HashMap<>();

        if (userService.findByEmail(dto.getEmail()) != null)
            throw new UserAlreadyExistException(MessageService.getMessage(lang, AuthMessageConstant.USER_WITH_THIS_EMAIL_EXIST));

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName() == null ? "" : dto.getMiddleName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(new Role(RoleEnums.USER.getRoleId(), RoleEnums.USER.getRoleName())));
        user.setEnabled(false);

        String generateRandomActivationCode = generateRandomActivationCode();
        user.setActivationCode(generateRandomActivationCode);

        user = userService.save(user).get("user");
        String token = jwtService.generateToken(user);
        jwtService.generateRefreshToken(user);

        emailService.sendActivationCode(user.getEmail(), generateRandomActivationCode);

        response.put("token", token);
        eventService.create(EventType.NEW_USER.getId(), user);
        return response;
    }

    public Map<String, Object> activate(Integer id, String code, Language lang) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) throw new AccessDeniedException("Пользователя не сущетсвует!");

        if (user.get().getActivationCode().equals(code)) {
            user.get().setEnabled(true);
            user.get().setActivationCode(null);
            userService.save(user.get());
            response.put("message", "Пользователь успешно активирован!");
        }
        else {
            response.put("message", "Введенный код не подходит!");
        }

        return response;
    }

    private String generateRandomActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
