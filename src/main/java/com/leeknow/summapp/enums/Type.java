package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum Type {
    SHORT(1),
    LONG(2);

    private final int id;

    Type(int id) {
        this.id = id;
    }
}
