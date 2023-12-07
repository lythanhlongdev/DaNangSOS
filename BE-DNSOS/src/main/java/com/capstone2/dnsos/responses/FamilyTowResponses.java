package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.User;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

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
    private LocalDate birthday;

    public static FamilyTowResponses mapper(User user) {
        return FamilyTowResponses.builder()
                .roleFamily(user.getRoleFamily())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday()).build();
    }
}
