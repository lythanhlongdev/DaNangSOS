package com.capstone2.dnsos.responses.main;


import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsRescueStationHaveSos {
    private Long id;
    private String rescueStationName;
    private Long total;
    private Long totalOtherStatus;
    private Long totalCompleted;
    private Long totalCancel;
    private Long totalUserCancel;
    private Long totalRescueCancel;
}
