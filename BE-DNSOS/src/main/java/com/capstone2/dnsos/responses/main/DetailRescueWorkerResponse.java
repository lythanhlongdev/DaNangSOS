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
public class DetailRescueWorkerResponse {

    private String status;
    private Long id;
    private String createdAt;
    private String updatedAt;
    @JsonProperty("infoUser")
    private DetailUserInWorkerResponse detailUserInWorkerResponse;
    @JsonProperty("infoRescueStation")
    private SimpleRescueStationResponse rescueStationResponse;

    public static DetailRescueWorkerResponse mapper(RescueStationRescueWorker currentWorker) {
        User user = currentWorker.getRescue().getUser();
        RescueStation rescueStation = currentWorker.getRescueStation();
        return DetailRescueWorkerResponse.builder()
                .id(currentWorker.getId())
                .status(currentWorker.isActivity() ? "Còn Làm việc" : "Đã Nghỉ làm")
                .createdAt(currentWorker.getCreatedAt().toString())
                .updatedAt(currentWorker.getUpdatedAt().toString())
                .detailUserInWorkerResponse(DetailUserInWorkerResponse.mapper(user))
                .rescueStationResponse(SimpleRescueStationResponse.mapper(rescueStation))
                .build();
    }
}
