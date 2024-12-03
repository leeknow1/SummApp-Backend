package com.leeknow.summapp.user.mapper;

import com.leeknow.summapp.user.dto.UserResponseDTO;
import com.leeknow.summapp.user.entity.User;

public class UserMapper {

    public static UserResponseDTO toResponseDtoUser(User user) {
        if (user != null) {
            UserResponseDTO userDto = new UserResponseDTO();
            userDto.setFirstName(user.getFirstName());
            userDto.setMiddleName(user.getMiddleName());
            userDto.setLastName(user.getLastName());
            return userDto;
        }
        return null;
    }
}
