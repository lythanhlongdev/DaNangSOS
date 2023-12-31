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
public class UserResponses {

    private String phoneNumber;
    private String passport;
    private String fullName;
    private String password;
    private LocalDate birthday;
    private String address;
    private Long familyId;
    private List<FamilyTowResponses> families;


    public static UserResponses mapper(User user, List<User> families) {
        UserResponses responses = UserResponses.builder()
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getCccdOrPassport())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .password(user.getPassword())
                .birthday(user.getBirthday())
                .address(user.getAddress())
                .familyId(user.getFamily().getFamilyId())
                .families(new ArrayList<>())
                .build();
        List<FamilyTowResponses> families1 = families.stream().map(FamilyTowResponses::mapper).toList();
        responses.setFamilies(families1);
        return responses;
    }
}
