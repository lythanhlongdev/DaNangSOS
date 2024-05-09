package com.capstone2.dnsos.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RescueStationDTO {

    @NotBlank(message = "Cần phải nhập số điện thoại")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Nhập số điện thoại sai định dạng")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Cần phải nhập số hộ chiếu")
    @Pattern(regexp = "^[A-Za-z0-9]{1,20}$", message = "Nhập số hộ chiếu sai định dạng")
    @JsonProperty("passport")
    private String passport;

    @NotBlank(message = "Cần phải nhập họ")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Cần phải nhập tên")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "Cần phải nhập mật khẩu")
    @Length(min = 6, max = 12, message = "Mật khẩu phải có độ dài từ 6 -12 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Mật khẩu phải chứa ký tự thường và in hoa")
    @JsonProperty("password")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("birthday")
    private LocalDate birthday;
    @NotBlank(message = "Cần phải nhập địa chỉ")

    @JsonProperty("address")
    private String address;

    //    @NotBlank(message = "phone family is requirement")
    @JsonProperty("phone_family") // cho phep null
    private String phoneFamily;

    @NotBlank(message = "Cần phải nhập mối quan hệ huyết thống")
    @JsonProperty("role_family")
    private String roleFamily;


    @JsonProperty( "rescue_stations_name")
    @NotBlank(message = "Cần phải nhập tên trạm cứu hộ" )
    @Length(max = 255, min = 6, message = "Tên trạm cứu hộ phải có độ dài từ 6 - 255 ký tự")
    private String rescueStationsName;

    @NotNull(message = "Cần phải nhập vĩ độ")
    private double latitude;
    @NotNull(message = "Cần phải nhập kinh độ")
    private double longitude;

//    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number_2")
    private String phoneNumber2;

//    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number_3")
    private String phoneNumber3;

    @NotBlank(message = "Cần phải nhập địa chỉ")
    @JsonProperty("rescue_stations_address")
    private String rescueStationsAddress;
    private String description;

}
