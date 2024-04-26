package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.enums.Status;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryByRescueStationResponsesv_1_1_0 {

    private Long historyId;
    private Status status;
    private String createdAt;
    private Double latitude;
    private Double longitude;
    private String img1;
    private String img2;
    private String img3;
    private String voice;
    private String note;
    private String updatedAt;
    private UserResponseNotFamily userResponses;

}
