package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.Rescue;
import lombok.*;


import java.time.LocalDate;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RescueResponse {

    private long rescueStationId;
    private long rescueId;
    private String phoneNumber;
    private String passport;
    private String firstName;
    private String lastName;
    private String birthday;
    private String address;
    private String roleFamily;
    private long familyId;
    private double latitude = 0.0;
    private double longitude = 0.0;

    public static RescueResponse rescueMapperResponse(Rescue rescue) {
        var user = rescue.getUser();
        return RescueResponse.builder()
                .rescueStationId(rescue.getRescueStation().getId())
                .rescueId(rescue.getId())
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .familyId(user.getFamily().getId())
                .build();
    }
}
