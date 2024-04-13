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
public class UpdateRescueDTO {


    @JsonProperty( "rescue_stations_name")
    @NotBlank(message = "rescue stations name is requirement" )
    @Length(max = 255, min = 6, message = "rescue stations name is greater than or equal to 6 and less than 255 characters")
    private String rescueStationsName;

    @NotNull(message = "Latitude is requirement")
    private double latitude;
    @NotNull(message = "Longitude is requirement")
    private double longitude;

    @JsonProperty("phone_number_2")
//    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    private String phoneNumber2;

    @JsonProperty("phone_number_3")
//    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    private String phoneNumber3;

    @NotBlank(message = "address is requirement")
    @JsonProperty("rescue_stations_address")
    private String rescueStationsAddress;
    private String description;


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


    @JsonProperty("phone_family") // cho phep null
    private String phoneFamily;

    @NotBlank(message = "role family is requirement")
    @JsonProperty("role_family")
    private String roleFamily;

    @NotBlank(message = "password is requirement")
    @Length(min = 6, max = 12, message = "Password have length min 6 max 12")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain both lowercase and uppercase letters")
    @JsonProperty("password")
    private String password;



}
