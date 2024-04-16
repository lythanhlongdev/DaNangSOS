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
public class HistoryResponse {

    private Long id;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String gps;
    private String note;
    private String rescueStation;
    private String user;

    public static HistoryResponse mapperResponse(History history){
        User user1 = history.getUser();
        return  HistoryResponse.builder()
                .id(history.getId())
                .status(history.getStatus().toString())
                .createdAt(history.getCreatedAt().toString())
                .updatedAt(history.getUpdatedAt().toString())
                .gps(String.format("%s, %s",history.getLatitude(), history.getLongitude()))
                .rescueStation(history.getRescueStation().getRescueStationsName())
                .user(String.format("%s %s", user1.getLastName(),user1.getLastName()))
                .build();
    }
}
