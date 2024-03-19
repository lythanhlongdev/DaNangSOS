package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryByRescueStationResponses {

    private Long historyId;
    private Status status;
    private String createdAt;
    private  String firstGPS;
    private  String endGPS;
    private String img1;
    private String img2;
    private String img3;
    private String voice;
    private String note;
    private String updatedAt;
    private UserNotPasswordResponses userResponses;

}
