package com.leeknow.summapp.user.mapper;

import com.leeknow.summapp.role.dto.RoleDTO;
import com.leeknow.summapp.role.entity.Role;
import com.leeknow.summapp.user.dto.UserDTO;
import com.leeknow.summapp.user.dto.UserResponseDTO;
import com.leeknow.summapp.user.entity.User;

import java.util.HashSet;
import java.util.Set;

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

    public static UserDTO toUserDTO(User user) {
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setMiddleName(user.getMiddleName());
            userDTO.setUsername(user.getUsername());

            Set<RoleDTO> roles = new HashSet<>();
            Set<Role> roleSet = user.getRoles();
            for (Role role : roleSet) {
                RoleDTO dto = new RoleDTO();
                dto.setRoleId(role.getRoleId());
                dto.setRoleName(role.getRoleName());
                roles.add(dto);
            }
            userDTO.setRoles(roles);
            return userDTO;
        }
        return null;
    }
}
