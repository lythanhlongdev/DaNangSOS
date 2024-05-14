package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkerDTO {

    @JsonProperty("id")
    @NotNull(message = "Trường ID không được để trống")
    private Long id;
    @NotBlank(message = "Passport is required!")
    @Pattern(regexp = "^[A-Za-z0-9]{1,20}$", message = "Invalid passport format!")
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

    //    @NotBlank(message = "phone family is requirement")
    @JsonProperty("phone_family") // cho phep null
    private String phoneFamily;

    @NotBlank
    @JsonProperty("role_family")
    private String roleFamily;


    @NotBlank(message = "Cần phải nhập mật khẩu xác nhận ")
    @Length(min = 6, max = 12, message = "Mật khẩu phải có độ dài từ 6 -12 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Mật khẩu phải chứa ký tự thường và in hoa")
    @JsonProperty("password")
    private String password;

}
