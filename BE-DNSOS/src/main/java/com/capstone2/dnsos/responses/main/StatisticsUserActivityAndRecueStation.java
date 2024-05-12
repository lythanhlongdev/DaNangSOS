package com.capstone2.dnsos.responses.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsUserActivityAndRecueStation {
    @JsonProperty("rescueStation")
    StatusRescueStationResponse rescueStationResponse;
    @JsonProperty("userActivity")
    StatisticsUserActivity statisticsUserActivity;
}
