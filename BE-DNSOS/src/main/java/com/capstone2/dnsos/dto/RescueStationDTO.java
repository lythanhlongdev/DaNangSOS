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


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RescueStationDTO {

    @NotNull(message = "user id is requirement !")
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty( "rescue_stations_name")
    @NotBlank(message = "rescue stations name is requirement" )
    @Length(max = 255, min = 6, message = "rescue stations name is greater than or equal to 6 and less than 255 characters")
    private String rescueStationsName;

    @NotNull(message = "Latitude is requirement")
    private double latitude;
    @NotNull(message = "Longitude is requirement")
    private double longitude;

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number_1")
    private String phoneNumber1;

//    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number_2")
    private String phoneNumber2;

//    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number_3")
    private String phoneNumber3;

    @NotBlank(message = "address is requirement")
    private String address;

    private String description;

}
