package com.leeknow.summapp.service;

import com.leeknow.summapp.event.service.EventService;
import com.leeknow.summapp.user.service.AuthenticationService;
import com.leeknow.summapp.user.service.EmailService;
import com.leeknow.summapp.user.service.UserService;
import com.leeknow.summapp.web.JWT.JwtService;
import com.leeknow.summapp.user.dto.UserLoginDTO;
import com.leeknow.summapp.user.dto.UserRegistrationDTO;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.event.enums.EventType;
import com.leeknow.summapp.common.enums.Language;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EventService eventService;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void signIn() {
        //given
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("test@gmail.com");
        loginDTO.setPassword("12345");

        User user = new User();
        user.setEmail("test@gmail.com");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user, null);

        //mock the calls
        when(userService.findByEmail(loginDTO.getEmail())).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        //when
        Map<String, Object> result = authenticationService.signIn(loginDTO);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("token"));
        assertNotNull(result.get("token"));
        assertNotEquals(result.get("token"), "");

        //verify
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(jwtService, times(1)).generateRefreshToken(user);
        verify(eventService, times(1)).create(EventType.SIGNING_IN.getId(), user);
    }

    @Test
    void signUp() {
        //given
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setEmail("test@gmail.com");
        registrationDTO.setPassword("12345");

        Map<String, User> createdMap = new HashMap<>();
        User createdUser = new User();
        createdUser.setUserId(1);
        createdUser.setEmail("test@gmail.com");
        createdMap.put("user", createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser, createdUser, null);

        //mock the calls
        when(userService.findByEmail(registrationDTO.getEmail())).thenReturn(null);
        when(userService.save(any(User.class))).thenReturn(createdMap);
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        //when
        Map<String, Object> result = authenticationService.signUp(registrationDTO, Language.RUSSIAN);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("token"));
        assertNotNull(result.get("token"));
        assertNotEquals(result.get("token"), "");

        //verify
        verify(userService, times(1)).findByEmail(registrationDTO.getEmail());
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(jwtService, times(1)).generateRefreshToken(createdUser);
        verify(eventService, times(1)).create(EventType.NEW_USER.getId(), createdUser);
    }
}
