package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum Status {

    CREATED(1),
    IN_PROGRESS(2),
    FINISHED(3);

    private final Integer id;

    Status(Integer id) {
        this.id = id;
    }
}
