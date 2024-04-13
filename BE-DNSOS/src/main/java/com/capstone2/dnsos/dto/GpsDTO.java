package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsDTO {

    @JsonProperty("history_id")
    @NotNull(message = "history id is requirement")
    private Long historyId;

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("user_phone_number")
    private String userPhoneNumber;

    @NotNull(message = "latitude id is requirement")
    private Double latitude;// vi do
    @NotNull(message = "longitude id is requirement")
    private Double longitude;// kinh do
}
