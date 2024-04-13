package com.capstone2.dnsos.enums;

import lombok.Getter;

@Getter
public enum Status {

    SYSTEM_RECEIVED(0),
    CONFIRMED(1),
    ON_THE_WAY(2),
    ARRIVED(3),
    COMPLETED(4),
    CANCELLED(5),
    CANCELLED_USER(6);
    final int value;

    Status(int value) {
        this.value = value;
    }
}
