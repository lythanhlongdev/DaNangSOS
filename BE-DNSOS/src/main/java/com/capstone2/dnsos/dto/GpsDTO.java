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
    @NotNull(message = "Cần phải nhập history id")
    private Long historyId;
    @NotNull(message = "Cần phải nhập vĩ độ")
    private Double latitude;// vi do
    @NotNull(message = "Cần phải nhập kinh độ")
    private Double longitude;// kinh do
}
