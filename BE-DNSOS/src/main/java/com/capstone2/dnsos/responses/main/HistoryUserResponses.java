package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
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
    private String phoneNumber;
    private String address;
    private Double kilometers;

    public static HistoryUserResponses mapperHistoryAndKilometers(History history, KilometerMin kilometerMin) {
        return HistoryUserResponses.builder()
                .status(history.getStatus())
                .rescueStationsID(history.getRescueStation().getId())
                .rescueStationsName(history.getRescueStation().getRescueStationsName())
                .address(history.getRescueStation().getAddress())
                .kilometers(kilometerMin.getKilometers())
                .build();
    }
}
