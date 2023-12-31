package com.capstone2.dnsos.enums;

import lombok.Getter;

@Getter
public enum Status {

    SYSTEM_RECEIVED(-1),
    CONFIRMED(0),
    ON_THE_WAY(1),
    ARRIVED(2),
    COMPLETED(3),
    CANCELLED(4),
    CANCELLED_USER(5);
    final int value;

    Status(int value) {
        this.value = value;
    }
}
