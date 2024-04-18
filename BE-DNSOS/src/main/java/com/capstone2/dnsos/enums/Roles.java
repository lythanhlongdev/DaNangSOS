package com.capstone2.dnsos.enums;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN(1),
    RECUE_STATION(2),
    RESCUE_WORKER(3),
    USER(4);

    final int value;

    Roles(int value) {
        this.value = value;
    }
}
