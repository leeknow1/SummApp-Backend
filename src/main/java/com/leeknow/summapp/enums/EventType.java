package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum EventType {

    SIGNING_IN(1),
    NEW_USER(2),
    APPLICATION_CREATED(3),
    APPLICATION_STATUS_CHANGED(4);

    private final int id;

    EventType(int id) {
        this.id = id;
    }
}
