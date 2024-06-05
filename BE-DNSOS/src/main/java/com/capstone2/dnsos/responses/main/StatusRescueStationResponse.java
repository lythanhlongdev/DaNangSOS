package com.capstone2.dnsos.responses.main;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusRescueStationResponse {
    private Long total;
    private Long online;
    private Long offline;
    private Long overload;
}
