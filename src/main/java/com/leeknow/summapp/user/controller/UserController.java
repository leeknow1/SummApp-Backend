package com.leeknow.summapp.user.controller;

import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.user.dto.UserDTO;
import com.leeknow.summapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.leeknow.summapp.user.mapper.UserMapper.toUserDTO;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @QueryMapping
    public List<UserDTO> users() {
        return userService.findAll();
    }

    @QueryMapping
    public UserDTO userById(@Argument Integer id) {
        return toUserDTO(userService.findById(id).orElse(null));
    }

    @SchemaMapping(typeName = "User", field = "applications")
    public List<ApplicationResponseDTO> applications(UserDTO user) {
        return userService.findApplicationByUser(user);
    }
}
