package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Cần phải nhập số điện thoại")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Nhập số điện thoại sai định dạng")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Cần phải nhập mật khẩu")
    @Length(min = 6, max = 12, message = "Mật khẩu phải có độ dài từ 6 -12 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Mật khẩu phải chứa ký tự thường và in hoa")
    @JsonProperty("password")
    private String password;

}
