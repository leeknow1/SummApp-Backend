package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum ApplicationType {

    SHORT(1),
    LONG(2);

    private final int id;

    ApplicationType(int id) {
        this.id = id;
    }
}
