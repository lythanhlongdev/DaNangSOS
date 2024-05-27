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

    @NotBlank(message = "Căn cước công dân không được để trống !")
    @Pattern(regexp = "^[0-9]{12}$", message = "Nhập sai mẫu giá trị, 0 tới 9 và đủ 12 số!")
    @JsonProperty("passport")
    private String passport;

    @NotBlank(message = "Cần phải nhập họ")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Cần phải nhập tên")
    @JsonProperty("last_name")
    private String lastName;


    @JsonProperty("birthday")
    private LocalDate birthday;

    @NotBlank(message = "Cần phải nhập địa chỉ")
    @JsonProperty("address")
    private String address;

    @JsonProperty("family_phone_number")
    private String familyPhoneNumber;
    @NotBlank
    @JsonProperty("role_family")
    private String roleFamily;

    @NotBlank(message = "Cần phải nhập mật khẩu")
    @Length(min = 6, max = 12, message = "Mật khẩu phải có độ dài từ 6 -12 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Mật khẩu phải chứa ký tự thường và in hoa")
    @JsonProperty("password")
    private  String password;

}
