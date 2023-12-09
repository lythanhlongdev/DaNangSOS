package com.capstone2.dnsos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
    @JsonProperty("phone_number")
    @NotNull(message = "phone number is requirement")
    private String phoneNumber;
    @NotNull(message = "latitude id is requirement")
    private Double latitude;// vi do
    @NotNull(message = "longitude id is requirement")
    private Double longitude;// kinh do
}
