package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotNull(message = "Phone number is requirement ")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotNull(message = "Password is requirement")
    private String password;


}
