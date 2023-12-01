package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.User;
import lombok.*;

import java.sql.Date;
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
    private Date birthday;
    private String address;
    private Long familyId;
    private List<FamilyTowResponses> families;


    public static UserResponses mapper(User user, List<User> families) {
        UserResponses responses = UserResponses.builder()
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getCccdOrPassport())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .birthday(user.getBirthday())
                .address(user.getAddress())
                .familyId(user.getFamilyId())
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
