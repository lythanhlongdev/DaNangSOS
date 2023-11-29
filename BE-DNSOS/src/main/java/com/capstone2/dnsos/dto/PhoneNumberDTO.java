package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDTO {

    @NotBlank(message = "phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
}
