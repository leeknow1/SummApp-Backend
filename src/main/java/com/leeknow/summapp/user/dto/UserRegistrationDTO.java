package com.leeknow.summapp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    @NotBlank(message = "Введите имя.")
    private String firstName;

    @NotBlank(message = "Введите фамилию.")
    private String lastName;

    private String middleName;

    @NotBlank(message = "Введите имя пользователя.")
    private String username;

    @NotBlank(message = "Введите почту.")
    @Email(message = "Неправильный формат почты.")
    private String email;

    @NotBlank(message = "Введите пароль.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Пароль должен состоять как минимум из 8 символов и содержать в себе одну букву и цифру.")
    private String password;

    private Integer roleId;
}
