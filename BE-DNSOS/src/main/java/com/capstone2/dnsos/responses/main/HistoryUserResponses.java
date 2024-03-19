package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.RescueStation;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryUserResponses {

    private Status status;
    private Long rescueStationsID;
    private String rescueStationsName;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;
    private String address;
    private Double kilometers;

    public static HistoryUserResponses mapperHistoryAndKilometers(History history, KilometerMin kilometerMin) {
        RescueStation rescueStation = history.getRescueStation();
        return HistoryUserResponses.builder()
                .status(history.getStatus())
                .rescueStationsID(rescueStation.getId())
                .rescueStationsName(rescueStation.getRescueStationsName())
                .phoneNumber1(rescueStation.getPhoneNumber1())
                .phoneNumber2(rescueStation.getPhoneNumber2())
                .phoneNumber3(rescueStation.getPhoneNumber3())
                .address(history.getRescueStation().getAddress())
                .kilometers(kilometerMin.getKilometers())
                .build();
    }
}
