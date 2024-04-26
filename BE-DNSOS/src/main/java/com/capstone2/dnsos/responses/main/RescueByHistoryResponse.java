package com.capstone2.dnsos.responses.main;


import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RescueByHistoryResponse {

    private String createdAt;
    private Long historyId;
    private Status status;
    private String gpsUser;
    private String gpsRescue;
    private String gpsRescueStation;
    private String img1;
    private String img2;
    private String img3;
    private String voice;
    private String note;
    private String updatedAt;
    @JsonProperty("info_user")
    private UserResponseNotFamily userResponses;

    public static RescueByHistoryResponse rescueMapperHistory(History history, Rescue rescue, HistoryMedia historyMedia) {
        User user = history.getUser();
        RescueStation rescueStation = history.getRescueStation();
        return RescueByHistoryResponse.builder()
                .createdAt(history.getCreatedAt().toString())
                .historyId(history.getId())
                .status(history.getStatus())
                .gpsUser(String.format("%s, %s", history.getLatitude(), history.getLongitude()))
                .gpsRescue(String.format("%s, %s", rescue.getLatitude(), rescue.getLongitude()))
                .gpsRescueStation(String.format("%s, %s", rescueStation.getLatitude(), rescueStation.getLongitude()))
                .img1(historyMedia == null ? " " : historyMedia.getImage1())
                .img2(historyMedia == null ? " " :historyMedia.getImage2())
                .img3(historyMedia == null ? " " :historyMedia.getImage3())
                .voice(historyMedia == null ? " " :historyMedia.getVoice())
                .userResponses(UserResponseNotFamily.mapper(user))
                .updatedAt(history.getUpdatedAt().toString())
                .build();
    }
}
