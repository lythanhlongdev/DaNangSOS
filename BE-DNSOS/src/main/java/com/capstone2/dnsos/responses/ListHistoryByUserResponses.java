package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.RescueStation;
import lombok.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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
    private String voice;
    private String note;
    private String image1;
    private String image2;
    private String image3;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private RescueStationResponses rescueStation;

    public static ListHistoryByUserResponses mapper(History history) {
        ListHistoryByUserResponses responses = ListHistoryByUserResponses.builder()
                .status(history.getStatus())
                .historyId(history.getHistoryId())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .voice(history.getVoice())
                .image1(history.getImage1())
                .image2(history.getImage2())
                .image3(history.getImage3())
                .createdAt(history.getCreatedAt())
                .updatedAt(history.getUpdatedAt())
                .build();
        RescueStation rescueStation = history.getRescueStation();
        RescueStationResponses stationResponses = RescueStationResponses.mapper(rescueStation);
        responses.setRescueStation(stationResponses);
        return responses;
    }
}
