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
public class RescueStationResponses2 {

    private Long rescueStationsId;
    private String rescueStationsName;
    private String captain;
    private String passport;
    private String birthday;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;
    private String rescueStationsAddress;
    private String gps;
    private String captainAddress;
    private String description;

    public static RescueStationResponses2 mapFromEntity(RescueStation rescueStation) {
        User user = rescueStation.getUser();
        String fullName = String.format("%s %s", user.getLastName(), user.getFirstName());
        return RescueStationResponses2.builder()
                .rescueStationsId(rescueStation.getId())
                .rescueStationsName(rescueStation.getRescueStationsName())
                .captain(fullName)
                .passport(user.getPassport())
                .birthday(user.getBirthday().toString())
                .phoneNumber1(rescueStation.getPhoneNumber1())
                .phoneNumber2(rescueStation.getPhoneNumber2())
                .phoneNumber3(rescueStation.getPhoneNumber3())
                .rescueStationsAddress(rescueStation.getAddress())
                .gps(String.format("%s, %s", rescueStation.getLatitude(), rescueStation.getLongitude()))
                .captainAddress(user.getAddress())
                .description(rescueStation.getDescription())
                .build();
    }
}
