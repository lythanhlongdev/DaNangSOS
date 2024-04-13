package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.enums.Status;
import lombok.*;

import java.time.LocalDateTime;


@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryByRescueStationResponses {

    private Status status;
    private Long historyId;
    private Double latitude;
    private Double longitude;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private HistoryMediaResponses historyMediaResponses;
    private UserNotPasswordResponses userNotPasswordResponses;

}
