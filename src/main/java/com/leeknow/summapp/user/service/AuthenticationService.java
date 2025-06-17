package com.leeknow.summapp.user.service;

import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.event.enums.EventType;
import com.leeknow.summapp.event.service.EventService;
import com.leeknow.summapp.message.service.MessageUtils;
import com.leeknow.summapp.role.entity.Role;
import com.leeknow.summapp.role.enums.RoleEnums;
import com.leeknow.summapp.user.dto.UserLoginDTO;
import com.leeknow.summapp.user.dto.UserRegistrationDTO;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.web.JWT.JwtService;
import com.leeknow.summapp.web.expections.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
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

import static com.leeknow.summapp.user.constant.AuthMessageConstant.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EventService eventService;
    private final EmailService emailService;
    private final MessageUtils messageUtils;

    public Map<String, Object> signIn(UserLoginDTO dto) {
        Map<String, Object> response = new HashMap<>();

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        User user = ((User) authenticate.getPrincipal());
        String token = jwtService.generateToken(user);
        jwtService.generateRefreshToken(user);

        response.put("token", token);
        eventService.create(EventType.SIGNING_IN.getId(), user);
        return response;
    }

    public String signUp(UserRegistrationDTO dto, Language lang) {

        User user = createUser(dto, lang);
        String token = generateToken(user);

        eventService.create(EventType.NEW_USER.getId(), user);
        return token;
    }

    @CachePut(value = "USER_CACHE", key = "#result.userId")
    public User createUser(UserRegistrationDTO dto, Language lang) {
        if (userService.findByEmail(dto.getEmail()) != null)
            throw new UserAlreadyExistException(messageUtils.getMessage(lang, USER_WITH_THIS_EMAIL_EXIST));

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
        emailService.sendActivationCode(user.getEmail(), generateRandomActivationCode);

        return userService.save(user);
    }

    private String generateToken(User user) {
        String token = jwtService.generateToken(user);
        jwtService.generateRefreshToken(user);
        return token;
    }

    public Map<String, Object> activate(Integer id, String code, Language lang) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) throw new AccessDeniedException(messageUtils.getMessage(lang, USER_DOES_NOT_EXIST));

        if (user.get().getActivationCode().equals(code)) {
            user.get().setEnabled(true);
            user.get().setActivationCode(null);
            userService.save(user.get());
            response.put("message", messageUtils.getMessage(lang, USER_SUCCESSFULLY_ACTIVATED));
        } else {
            response.put("message", messageUtils.getMessage(lang, USER_CODE_IS_INCORRECT));
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
