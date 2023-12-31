package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.RescueStation;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RescueStationResponses {

    private Long rescueStationsId;
    private String rescueStationsName;
    private String phoneNumber;
    private String address;
    private String captain;
    private Double latitude;// vi do
    private Double longitude;// kinh do
    private String description;

    public static  RescueStationResponses mapFromEntity(RescueStation rescueStation) {
        return RescueStationResponses.builder()
                .rescueStationsId(rescueStation.getRescueStationsId())
                .rescueStationsName(rescueStation.getRescueStationsName())
                .captain(rescueStation.getCaptain())
                .phoneNumber(rescueStation.getPhoneNumber())
                .address(rescueStation.getAddress())
                .latitude(rescueStation.getLatitude())
                .longitude(rescueStation.getLongitude())
                .description(rescueStation.getDescription())
                .build();
    }
}
