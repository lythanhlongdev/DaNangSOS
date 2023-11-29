package com.capstone2.dnsos.responses;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponsesEntity {
    private String message;
    private int  status;
    private Object Data;
}
