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
public class RescueStationResponses {

    private Long rescueStationsId;
    private String avatar;
    private String rescueStationsName;
    private String captain;
    private String passport;
    private String birthday;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;
    private String rescueStationsAddress;
    private String rescueStationGPS;
    private String captainAddress;
    private String description;
    private String status;

    public static RescueStationResponses mapFromEntity(RescueStation rescueStation) {
        User user = rescueStation.getUser();
        String fullName = String.format("%s %s", user.getLastName(), user.getFirstName());
        String avatarName = rescueStation.getAvatar();
        if ( avatarName == null || avatarName.isBlank()) {
            avatarName = ""; // Or provide a default value here if needed
        }
        return RescueStationResponses.builder()
                .avatar(avatarName)
                .status(rescueStation.getStatus().toString())
                .rescueStationsId(rescueStation.getId())
                .rescueStationsName(rescueStation.getRescueStationsName())
                .captain(fullName)
                .passport(user.getPassport())
                .birthday(user.getBirthday().toString())
                .phoneNumber1(rescueStation.getPhoneNumber1())
                .phoneNumber2(rescueStation.getPhoneNumber2())
                .phoneNumber3(rescueStation.getPhoneNumber3())
                .rescueStationsAddress(rescueStation.getAddress())
                .rescueStationGPS(String.format("%s, %s", rescueStation.getLatitude(), rescueStation.getLongitude()))
                .captainAddress(user.getAddress())
                .description(rescueStation.getDescription())
                .build();
    }
}
