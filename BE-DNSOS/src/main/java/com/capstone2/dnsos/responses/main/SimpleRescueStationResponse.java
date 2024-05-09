package com.capstone2.dnsos.responses.main;


import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRescueStationResponse {

    private Long id;
    private String name;
    private String captain;
    private String phoneNumber;
    private String address;
    private boolean isActivity;

    public static SimpleRescueStationResponse mapper(RescueStation rescueStation) {
        User user = rescueStation.getUser();
        return SimpleRescueStationResponse.builder()
                .id(rescueStation.getId())
                .name(rescueStation.getRescueStationsName())
                .captain(String.format("%s %s", user.getLastName(), user.getFirstName()))
                .phoneNumber(rescueStation.getPhoneNumber1())
                .address(rescueStation.getAddress())
                .isActivity(rescueStation.getIsActivity())
                .build();
    }

}
