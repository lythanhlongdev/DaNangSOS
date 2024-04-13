package com.capstone2.dnsos.dto.history;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    @NotNull(message = "latitude id is requirement")
    private Double latitude;// vi do
    @NotNull(message = "longitude id is requirement")
    private Double longitude;// kinh do

}
