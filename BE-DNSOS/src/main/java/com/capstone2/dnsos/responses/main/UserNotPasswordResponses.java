package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNotPasswordResponses {

    private String avatar;
    private String phoneNumber;
    private String passport;
    private String fullName;
    private String birthday;
    private String address;
    private Long familyId;
    private List<FamilyResponses> families;

    public static UserNotPasswordResponses mapper(User user, List<User> families) {
        List<FamilyResponses> familyResponses = families.stream().map(FamilyResponses::mapperUser).toList();
        return UserNotPasswordResponses.builder()
                .avatar(user.getAvatar())
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .families(familyResponses)
                .build();

    }
}
