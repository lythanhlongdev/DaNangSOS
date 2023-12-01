package com.capstone2.dnsos;

import com.capstone2.dnsos.enums.Status;

public class Test {

    public static void main(String[] args) {

        if (Status.ARRIVED.getValue() < Status.SYSTEM_RECEIVED.getValue()){
            System.out.printf("ok");
        }else {
            System.out.printf("no");
        }
    }
}
