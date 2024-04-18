package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryByGPSResponse {

    private Long id;
    private String updatedAt;
    private String status;
    private String gps;
    private String rescueStation;
    private String user;

    public static HistoryByGPSResponse mapperResponse(History history){
        User user1 = history.getUser();
        return  HistoryByGPSResponse.builder()
                .id(history.getId())
                .status(history.getStatus().toString())
                .updatedAt(history.getUpdatedAt().toString())
                .gps(String.format("%s, %s",history.getLatitude(), history.getLongitude()))
                .rescueStation(history.getRescueStation().getRescueStationsName())
                .user(String.format("%s %s", user1.getLastName(),user1.getFirstName()))
                .build();
    }
}
