package com.capstone2.dnsos.repositories.main;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import lombok.*;
@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HistoryUserResponses {
    private String address;
    private Double kilometers;
    private Status status;
    private Long rescueStationsID;
    private String rescueStationsName;
    private String phoneNumber;
    public static HistoryUserResponses mapperHistoryAndKilometers(History history, KilometerMin kilometerMin) {
        return HistoryUserResponses.builder()
                .status(history.getStatus())
                .rescueStationsID(history.getRescueStation().getId())
                .rescueStationsName(history.getRescueStation().getRescueStationsName())
                .phoneNumber(history.getRescueStation().getPhoneNumber1())
                .address(history.getRescueStation().getAddress())
                .kilometers(kilometerMin.getKilometers())
                .build();
    }
}