package com.leeknow.application.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN(1, "ADMIN"),
    USER(2, "USER"),
    EMPLOYEE(3, "EMPLOYEE");

    private final Integer roleId;
    private final String roleName;

    Role(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static String getRoleNameById(Integer roleId) {
        for (Role role : Role.values()) {
            if (role.getRoleId().equals(roleId))
                return role.getRoleName();
        }
        return "";
    }
}
