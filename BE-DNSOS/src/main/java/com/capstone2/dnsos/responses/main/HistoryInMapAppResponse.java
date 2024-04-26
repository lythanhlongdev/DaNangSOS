package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryInMapAppResponse {

    private String id;
    private String updatedAt;
    private String status;
    private String userGps;
    private String img1;
    private String img2;
    private String img3;
    private String voice;
    private String createdAt;
    @JsonProperty("Info_rescue_station")
    private RescueStationResponses stationResponses;

    // Cân thận chỗ này nếu có 2  3 người scan thí app sử lý ra sao
    @JsonProperty("recue_worker_in_sos")
    private List<RescueWorkerResponse> rescueWorkerResponses;

    public static HistoryInMapAppResponse mapperInMap(RescueStation rescueStation, History history, HistoryMedia media, List<User> usersIsRecueWorker) {
        List<RescueWorkerResponse> workerResponses = usersIsRecueWorker.stream().map(RescueWorkerResponse::mapperUserIsRescueWorker).toList();
        return HistoryInMapAppResponse.builder()
                .id(history.getId().toString())
                .status(history.getStatus().toString())
                .userGps(String.format("%s, %s", history.getLatitude(), history.getLongitude()))
                .img1(media == null ? " " : media.getImage1())
                .img2(media == null ? " " : media.getImage2())
                .img3(media == null ? " " : media.getImage3())
                .voice(media == null ? " " : media.getVoice())
                .createdAt(history.getCreatedAt().toString())
                .updatedAt(history.getUpdatedAt().toString())
                .stationResponses(RescueStationResponses.mapFromEntity(rescueStation))
                .rescueWorkerResponses(workerResponses)
                .build();
    }
}
