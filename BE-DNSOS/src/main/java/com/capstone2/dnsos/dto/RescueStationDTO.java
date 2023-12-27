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
public class RescueStationDTO {


    @JsonProperty( "rescue_stations_name")
    @NotBlank(message = "rescue stations name is requirement" )
    @Length(max = 255, min = 6, message = "rescue stations name is greater than or equal to 6 and less than 255 characters")
    private String rescueStationsName;


    @NotBlank(message = "captain name is requirement")
    private  String captain;

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 6, max = 12, message = "Password have length min 6 max 12")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain both lowercase and uppercase letters")
    @JsonProperty("password")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @NotBlank(message = "address is requirement")
    private String address;

    private String description;

}
