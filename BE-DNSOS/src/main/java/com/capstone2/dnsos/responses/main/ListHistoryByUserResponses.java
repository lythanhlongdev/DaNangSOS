package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListHistoryByUserResponses {

    private Status status;
    private Long historyId;
    private Double latitude;// vi do
    private Double longitude;// kinh do
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private HistoryMediaResponses mediaResponses;
    private RescueStationResponses rescueStation;


    public static ListHistoryByUserResponses mapFromEntities(History history, HistoryMedia historyMedia) {
        return builder()
                .status(history.getStatus())
                .historyId(history.getHistoryId())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .note(history.getNote())
                .createdAt(history.getCreatedAt())
                .updatedAt(history.getUpdatedAt())
                .rescueStation(RescueStationResponses.mapFromEntity(history.getRescueStation()))
                .mediaResponses(HistoryMediaResponses.mapFromEntity(historyMedia))
                .build();
    }
}
