package com.leeknow.summapp.service;

import com.leeknow.summapp.configuration.CustomUserDetails;
import com.leeknow.summapp.configuration.JWT.JwtService;
import com.leeknow.summapp.dto.UserLoginDTO;
import com.leeknow.summapp.dto.UserRegistrationDTO;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.ApplicationStatus;
import com.leeknow.summapp.enums.ApplicationType;
import com.leeknow.summapp.enums.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        //mock the calls
        when(userService.findByEmail(loginDTO.getEmail())).thenReturn(user);
        when(jwtService.generateToken(any(CustomUserDetails.class))).thenReturn("token");

        //when
        Map<String, Object> result = authenticationService.signIn(loginDTO);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("token"));
        assertNotNull(result.get("token"));
        assertNotEquals(result.get("token"), "");

        //verify
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).findByEmail(loginDTO.getEmail());
        verify(jwtService, times(1)).generateToken(any(CustomUserDetails.class));
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

        //mock the calls
        when(userService.findByEmail(registrationDTO.getEmail())).thenReturn(null);
        when(userService.save(any(User.class))).thenReturn(createdMap);
        when(jwtService.generateToken(any(CustomUserDetails.class))).thenReturn("token");

        //when
        Map<String, Object> result = authenticationService.signUp(registrationDTO);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("token"));
        assertNotNull(result.get("token"));
        assertNotEquals(result.get("token"), "");

        //verify
        verify(userService, times(1)).findByEmail(registrationDTO.getEmail());
        verify(jwtService, times(1)).generateToken(any(CustomUserDetails.class));
        verify(jwtService, times(1)).generateRefreshToken(createdUser);
        verify(eventService, times(1)).create(EventType.NEW_USER.getId(), createdUser);
    }
}
