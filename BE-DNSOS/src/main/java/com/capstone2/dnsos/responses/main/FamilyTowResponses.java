package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyTowResponses {

    private Long  id;
    private String roleFamily;
    private String fullName;
    private String phoneNumber;
    private String birthday;

    public static FamilyTowResponses mapper(User user) {
        return FamilyTowResponses.builder()
                .id(user.getFamily().getId())
                .roleFamily(user.getRoleFamily())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday().toString())
                .build();
    }
}
