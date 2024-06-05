package com.capstone2.dnsos.responses.main;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageStatisticsAllRescueStation {
    private List<StatisticsRescueStationHaveSos> statisticsRescueStationHaveSos;
    private int elements;
}
