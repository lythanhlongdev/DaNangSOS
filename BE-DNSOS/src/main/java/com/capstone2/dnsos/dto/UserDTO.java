package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Phone number is requirement !")
    //    @Pattern(regexp = "")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotBlank(message = "Passport  is requirement !")
    @JsonProperty("passport")
    private String passport;
    @NotBlank(message = "Name number is requirement !")
    @JsonProperty("full_name")
    private String fullName;
    @NotBlank(message = "password is requirement !")
    @Length(min = 6, max = 12, message = "Password have length min 6 max 12")
//    @Pattern(regexp = "")
    @JsonProperty("password")
    private String password;
    @JsonProperty("retype_password")

    private String retypePassword;
    @JsonProperty("birthday")
    private LocalDate birthday;
    @NotBlank(message = "address is requirement ")
    @JsonProperty("address")
    private String address;

    @JsonProperty("family_phone_number")
    private String familyPhoneNumber;
    @NotBlank
    @JsonProperty("role_family")
    private String roleFamily;
}
