package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RescueStationInDetailHistoryResponse {

    private Long id;
    private String avatar;
    private String rescueStationsName;
    private String captain;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;
    private String address;
    private String gps;
    private String createdAt;
    private boolean isActivity;

    public static RescueStationInDetailHistoryResponse mapper(RescueStation rescueStation) {
        User user = rescueStation.getUser();
        return RescueStationInDetailHistoryResponse.builder()
                .id(rescueStation.getId())
                .avatar(rescueStation.getAvatar() == null ? "" : rescueStation.getAvatar())
                .captain(String.format("%s %s", user.getLastName(), user.getFirstName()))
                .rescueStationsName(rescueStation.getRescueStationsName())
                .phoneNumber1(rescueStation.getPhoneNumber1() == null ? "" : rescueStation.getPhoneNumber1())
                .phoneNumber2(rescueStation.getPhoneNumber2() == null ? "" : rescueStation.getPhoneNumber2())
                .phoneNumber3(rescueStation.getPhoneNumber3() == null ? "" : rescueStation.getPhoneNumber3())
                .address(rescueStation.getAddress())
                .gps(String.format("%s, %s", rescueStation.getLatitude(), rescueStation.getLongitude()))
                .isActivity(rescueStation.getIsActivity())
                .createdAt(rescueStation.getCreatedAt().toString())
                .build();
    }
}
