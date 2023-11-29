package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyTowResponses {

    private String roleFamily;
    private String fullName;
    private String phoneNumber;
    private Date birthday;

    public static FamilyTowResponses mapper(User user) {
        return FamilyTowResponses.builder()
                .roleFamily(user.getRoleFamily())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday()).build();
    }
}
