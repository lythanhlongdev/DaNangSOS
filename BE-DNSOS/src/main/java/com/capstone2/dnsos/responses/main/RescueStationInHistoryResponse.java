package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.RescueStation;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RescueStationInHistoryResponse {

    private Long rescueStationsId;
    private String rescueStationsName;
    private String captain;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;
    private String address;
    private String gps;
    private String description;

    public static RescueStationInHistoryResponse mapFromEntity(RescueStation rescueStation) {
        String fullName = rescueStation.getUser().getFirstName() +" "+ rescueStation.getUser().getLastName();
        return RescueStationInHistoryResponse.builder()
                .rescueStationsId(rescueStation.getId())
                .rescueStationsName(rescueStation.getRescueStationsName())
                .captain(fullName)
                .phoneNumber1(rescueStation.getPhoneNumber1())
                .phoneNumber2(rescueStation.getPhoneNumber2())
                .phoneNumber3(rescueStation.getPhoneNumber3())
                .address(rescueStation.getAddress())
                .gps(String.format("%s, %s", rescueStation.getLatitude(), rescueStation.getLongitude()))
                .description(rescueStation.getDescription())
                .build();
    }
}
