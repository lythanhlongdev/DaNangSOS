package com.capstone2.dnsos.enums;

import lombok.Getter;

@Getter
public enum StatusRescueStation {

    ACTIVITY(1),
    PAUSE(2),
    OVERLOAD(3);
    final int value;
    StatusRescueStation(int value) {
        this.value = value;
    }
}
