package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityDTO {

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Security code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "security code must be a 6-digit number")
    @DecimalMin(value = "000000", inclusive = true, message = "security code must be greater than or equal to 000000")
    @DecimalMax(value = "999999", inclusive = true, message = "security code must be less than or equal to 999999")
    @JsonProperty("security_code")
    private String securityCode;

}
