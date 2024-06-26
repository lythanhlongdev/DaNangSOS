package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryByRescueStationIdResponses {

    private String phoneNumber;
    private String fullName;
    private LocalDate birthday;
    private String address;
    private Long familyId;
    private List<FamilyTowResponses> families;

    public static UserHistoryByRescueStationIdResponses mapper(User user, List<User> families) {
        UserHistoryByRescueStationIdResponses responses = UserHistoryByRescueStationIdResponses.builder()
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .birthday(user.getBirthday())
                .address(user.getAddress())
                .familyId(user.getFamily().getId())
                .families(new ArrayList<>())
                .build();
        List<FamilyTowResponses> families1 = families.stream().map(FamilyTowResponses::mapper).toList();

//        List<FamilyResponses> families2 = new ArrayList<>();
//        for (User item : families) {
//            families2.add(FamilyResponses.mapperUser(item));
//        }
        responses.setFamilies(families1);
        return responses;
    }
}
