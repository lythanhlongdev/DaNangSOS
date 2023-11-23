package com.capstone2.dnsos.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is requirement !")
    //    @Pattern(regexp = "")
    private String phoneNumber;

    @NotBlank(message = "Password is requirement !")
    @Length(min = 6, max = 12, message = "Password have length min 6 max 12")
    @JsonProperty("password")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @NotBlank(message = "address is requirement")
    private String address;

    private String description;

}
