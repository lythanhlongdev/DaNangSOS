package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryByUserResponses {

    private Long historyId;
    private Status status;
    private String createdAt;
    private double latitude;
    private double longitude;
    private String img1;
    private String img2;
    private String img3;
    private String voice;
    private String note;
    private String updatedAt;
    private RescueStationInHistoryResponse rescueStation;

    public static HistoryByUserResponses mapFromEntities(History history, HistoryMedia historyMedia) {
        return builder()
                .status(history.getStatus())
                .historyId(history.getId())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .note(history.getNote())
                .img1(historyMedia.getImage1())
                .img2(historyMedia.getImage2())
                .img3(historyMedia.getImage3())
                .voice(historyMedia.getVoice())
                .createdAt(history.getCreatedAt().toString())
                .updatedAt(history.getUpdatedAt().toString())
                .rescueStation(RescueStationInHistoryResponse.mapFromEntity(history.getRescueStation()))
                .build();
    }
}
