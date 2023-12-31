package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.common.ResultKM;
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

    public static HistoryUserResponses mapperHistoryAndKilometers(History history, ResultKM resultKM) {
        return HistoryUserResponses.builder()
                .status(history.getStatus())
                .rescueStationsID(history.getRescueStation().getRescueStationsId())
                .rescueStationsName(history.getRescueStation().getRescueStationsName())
                .phoneNumber(history.getRescueStation().getPhoneNumber())
                .address(history.getRescueStation().getAddress())
                .kilometers(resultKM.getKilometers())
                .build();
    }
}
