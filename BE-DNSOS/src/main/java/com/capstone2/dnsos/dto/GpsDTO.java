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
    @NotNull(message = "latitude id is requirement")
    private Double latitude;// vi do
    @NotNull(message = "longitude id is requirement")
    private Double longitude;// kinh do
}
