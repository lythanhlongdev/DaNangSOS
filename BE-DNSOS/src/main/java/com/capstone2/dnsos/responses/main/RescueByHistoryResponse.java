package com.capstone2.dnsos.responses.main;


import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.models.main.User;
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
    private String img1;
    private String img2;
    private String img3;
    private String voice;
    private String note;
    private String updatedAt;
    @JsonProperty("info_user")
    private UserResponev_1_1_0 userResponses;

    public static RescueByHistoryResponse rescueMapperHistory(History history, Rescue rescue, HistoryMedia historyMedia) {
        User user = history.getUser();
        return RescueByHistoryResponse.builder()
                .createdAt(history.getCreatedAt().toString())
                .historyId(history.getId())
                .status(history.getStatus())
                .gpsUser(String.format("%s, %s", history.getLatitude(), history.getLongitude()))
                .gpsRescue(String.format("%s, %s", rescue.getLatitude(), rescue.getLongitude()))
                .img1(historyMedia == null ? "" : historyMedia.getImage1())
                .img2(historyMedia == null ? "" :historyMedia.getImage2())
                .img3(historyMedia == null ? "" :historyMedia.getImage3())
                .voice(historyMedia == null ? "" :historyMedia.getVoice())
                .userResponses(UserResponev_1_1_0.mapper(user))
                .build();
    }
}
