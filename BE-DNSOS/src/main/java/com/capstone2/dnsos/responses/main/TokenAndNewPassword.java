package com.capstone2.dnsos.responses.main;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TokenAndNewPassword {

    private  String newPassword;
    private  String token;
}
