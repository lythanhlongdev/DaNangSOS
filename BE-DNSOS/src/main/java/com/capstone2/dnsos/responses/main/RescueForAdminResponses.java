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
public class RescueForAdminResponses {

    private String createdAt;
    private Long   rescueStationsId;
    private String rescueStationsName;
    private String captain;
    private String phoneNumber;
    private String phoneNumber2;
    private String phoneNumber3;
    private String address;
    private String gps;
    private String description;
    private String updatedAt;
    private boolean isActivity;
    private UserForAdminResponses userForAdminResponses;

    public static RescueForAdminResponses mapFromEntity(RescueStation rescueStation) {
        User user = rescueStation.getUser();
        String fullName = String.format("%s %s", user.getLastName(), user.getFirstName());
        return RescueForAdminResponses.builder()
                .createdAt(rescueStation.getCreatedAt().toString())
                .rescueStationsId(rescueStation.getId())
                .rescueStationsName(rescueStation.getRescueStationsName())
                .captain(fullName)
                .phoneNumber(user.getPhoneNumber())
                .phoneNumber2(rescueStation.getPhoneNumber2())
                .phoneNumber3(rescueStation.getPhoneNumber3())
                .address(rescueStation.getAddress())
                .gps(String.format("%s, %s", rescueStation.getLatitude(), rescueStation.getLongitude()))
                .description(rescueStation.getDescription())
                .updatedAt(rescueStation.getUpdatedAt().toString())
                .isActivity(rescueStation.getIsActivity())
                .userForAdminResponses(UserForAdminResponses.mapper(user))
                .build();
    }
}
