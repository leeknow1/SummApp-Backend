package com.leeknow.summapp.role.enums;

import lombok.Getter;

@Getter
public enum RoleRight {
    READ(1),
    WRITE(2)
    ;

    private final int id;

    RoleRight(int id) {
        this.id = id;
    }
}
