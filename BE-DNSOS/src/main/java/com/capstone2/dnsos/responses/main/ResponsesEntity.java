package com.capstone2.dnsos.responses.main;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponsesEntity {
    private String message;
    private int  status;
    private Object Data;
}
