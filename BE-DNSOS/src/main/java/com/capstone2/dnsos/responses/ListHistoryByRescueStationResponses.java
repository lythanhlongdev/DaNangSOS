package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListHistoryByRescueStationResponses {

    private Status status;
    private Long historyId;
    private Double latitude;// vi do
    private Double longitude;// kinh do
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private HistoryMediaResponses mediaResponses;
    private UserResponses userResponses;


    public static ListHistoryByRescueStationResponses mapper(History history, HistoryMedia historyMedia, List<User> families) {
        ListHistoryByRescueStationResponses responses = ListHistoryByRescueStationResponses.builder()
                .status(history.getStatus())
                .historyId(history.getHistoryId())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .note(history.getNote())
                .createdAt(history.getCreatedAt())
                .updatedAt(history.getUpdatedAt())
                .build();
        responses.setUserResponses(UserResponses.mapper(history.getUser(), families));
        responses.setMediaResponses(HistoryMediaResponses.mapper(historyMedia));
        return responses;
    }
}
