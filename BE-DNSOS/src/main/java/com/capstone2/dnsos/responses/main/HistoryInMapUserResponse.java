package com.capstone2.dnsos.responses.main;

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
public class HistoryInMapUserResponse {

    @JsonProperty("Info_rescue_station")
    private RescueStationResponses stationResponses;

    @JsonProperty("recue_worker_in_sos")
    private List<RescueWorkerResponse> rescueWorkerResponses;

    public static HistoryInMapUserResponse mapperInMap(RescueStation rescueStation, List<User> usersIsRecueWorker) {
        List<RescueWorkerResponse> workerResponses = usersIsRecueWorker.stream().map(RescueWorkerResponse::mapperUserIsRescueWorker).toList();
        return HistoryInMapUserResponse.builder()
                .stationResponses(RescueStationResponses.mapFromEntity(rescueStation))
                .rescueWorkerResponses(workerResponses)
                .build();
    }
}
