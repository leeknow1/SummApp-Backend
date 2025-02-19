package com.leeknow.summapp.user.dto;

import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.role.dto.RoleDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private Set<RoleDTO> roles;
    private List<ApplicationResponseDTO> applications;
}
