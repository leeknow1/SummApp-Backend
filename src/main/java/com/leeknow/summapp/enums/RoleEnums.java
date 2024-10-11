package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum RoleEnums {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER"),
    EMPLOYEE(3, "ROLE_EMPLOYEE");

    private final Integer roleId;
    private final String roleName;

    RoleEnums(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static String getRoleNameById(Integer roleId) {
        for (RoleEnums role : RoleEnums.values()) {
            if (role.getRoleId().equals(roleId))
                return role.getRoleName();
        }
        return "";
    }
}
