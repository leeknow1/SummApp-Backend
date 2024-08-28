package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        //given
        User user = new User();

        User createdUser = new User();
        createdUser.setUserId(1);

        //mock the calls
        when(userRepository.save(user)).thenReturn(createdUser);

        //when
        Map<String, User> result = userService.save(user);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("user"));
        assertNotNull(result.get("user"));
        assertEquals(result.get("user").getUserId(), 1);

        //verify
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findByEmail() {
        //given
        User user = new User();
        user.setEmail("test@gmail.com");

        //mock the calls
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        //when
        User foundUser = userService.findByEmail("test@gmail.com");

        //then
        assertNotNull(foundUser);
        assertEquals(foundUser.getEmail(), user.getEmail());

        //verify
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
    }

    @Test
    void getCurrentUser() {
        //given
        User user = new User();
        user.setEmail("test@gmail.com");

        Authentication authentication = new UsernamePasswordAuthenticationToken("test@gmail.com", null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //mock the calls
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        //when
        User currentUser = userService.getCurrentUser();

        //then
        assertNotNull(currentUser);
        assertEquals(currentUser.getEmail(), user.getEmail());

        //verify
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
    }
}
