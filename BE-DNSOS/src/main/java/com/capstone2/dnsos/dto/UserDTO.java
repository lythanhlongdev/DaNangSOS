package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Passport  is requirement !")
    @JsonProperty("passport")
    private String passport;

    @NotBlank(message = "first name is requirement !")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "last name is requirement !")
    @JsonProperty("last_name")
    private String lastName;


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

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 6, max = 12, message = "Password have length min 6 max 12")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain both lowercase and uppercase letters")
    @JsonProperty("password")
    private  String password;

}
