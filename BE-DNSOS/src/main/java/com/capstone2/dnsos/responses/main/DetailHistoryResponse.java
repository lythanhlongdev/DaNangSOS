package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailHistoryResponse {
    private String status;
    private Long id;
    private String createdAt;
    private String updatedAt;
    private String gps;
    private String img1;
    private String img2;
    private String img3;
    private String voice;
    @JsonProperty("infoUser")
    UserInDetailHistoryResponse userInDetailHistoryResponse;
    @JsonProperty("infoRescueStation")
    RescueStationInDetailHistoryResponse rescueStationInDetailHistoryResponse;
    @JsonProperty("infoRescueWorker")
    RescueWorkerDetailHistoryResponse rescueWorkerDetailHistoryResponse;

    public static DetailHistoryResponse mapper(History history, HistoryMedia historyMedia, RescueStationRescueWorker rescueStationRescueWorker) {
        User user = history.getUser();
        RescueStation rescueStation = history.getRescueStation();
        return DetailHistoryResponse.builder()
                .status(history.getStatus().toString())
                .id(history.getId())
                .createdAt(history.getCreatedAt().toString())
                .updatedAt(history.getUpdatedAt().toString())
                .gps(String.format("%s, %s", history.getLatitude(), history.getLongitude()))
                .img1(historyMedia.getImage1() == null ? "" : historyMedia.getImage1())
                .img2(historyMedia.getImage2() == null ? "" : historyMedia.getImage2())
                .img3(historyMedia.getImage3() == null ? "" : historyMedia.getImage3())
                .voice(historyMedia.getVoice() == null ? "" : historyMedia.getVoice())
                .userInDetailHistoryResponse(UserInDetailHistoryResponse.mapper(user))
                .rescueStationInDetailHistoryResponse(RescueStationInDetailHistoryResponse.mapper(rescueStation))
                .rescueWorkerDetailHistoryResponse(RescueWorkerDetailHistoryResponse.mapper(rescueStationRescueWorker))
                .build();
    }

    public static DetailHistoryResponse mapper(History history, HistoryMedia historyMedia) {
        User user = history.getUser();
        RescueStation rescueStation = history.getRescueStation();
        return DetailHistoryResponse.builder()
                .status(history.getStatus().toString())
                .id(history.getId())
                .createdAt(history.getCreatedAt().toString())
                .updatedAt(history.getUpdatedAt().toString())
                .gps(String.format("%s, %s", history.getLatitude(), history.getLongitude()))
                .img1(historyMedia.getImage1() == null ? "" : historyMedia.getImage1())
                .img2(historyMedia.getImage2() == null ? "" : historyMedia.getImage2())
                .img3(historyMedia.getImage3() == null ? "" : historyMedia.getImage3())
                .voice(historyMedia.getVoice() == null ? "" : historyMedia.getVoice())
                .userInDetailHistoryResponse(UserInDetailHistoryResponse.mapper(user))
                .rescueStationInDetailHistoryResponse(RescueStationInDetailHistoryResponse.mapper(rescueStation))
                .build();
    }
}
