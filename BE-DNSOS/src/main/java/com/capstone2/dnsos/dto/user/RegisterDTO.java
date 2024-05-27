package com.capstone2.dnsos.dto.user;

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
public class RegisterDTO {

//    private static final String regexViettel = "^(0|\\+84)(86|96|97|98|32|33|34)\\d{7}$";
//    private static final String regexVinaphone = "^(0|\\+84)(91|94|88)\\d{7}$";
//    private static final String regexMobifone = "^(0|\\+84)(90|93)\\d{7}$";
//    private static final String regexVietnamobile = "^(0|\\+84)92\\d{7}$";


    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Căn cước công dân không được để trống !")
    @Pattern(regexp = "^[0-9]{12}$", message = "Nhập sai mẫu giá trị, 0 tới 9 và đủ 12 số!")
    @JsonProperty("passport")
    private String passport;

    @NotBlank(message = "first name is requirement !")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "last name is requirement !")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "password is requirement !")
    @Length(min = 6, max = 12, message = "Password have length min 6 max 12")

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 6, max = 12, message = "Password have length min 6 max 12")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain both lowercase and uppercase letters")
    @JsonProperty("password")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("birthday")
    private LocalDate birthday;
    @NotBlank(message = "address is requirement ")
    @JsonProperty("address")
    private String address;

    //    @NotBlank(message = "phone family is requirement")
    @JsonProperty("phone_family") // cho phep null
    private String phoneFamily;

    @NotBlank
    @JsonProperty("role_family")
    private String roleFamily;
}
