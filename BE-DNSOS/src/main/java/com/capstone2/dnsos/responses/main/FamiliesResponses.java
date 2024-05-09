package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FamiliesResponses {
    private String roleFamily;
    private String fullName;
    private String phoneNumber;
    private String birthday;

    public static FamiliesResponses mapperUser(User user) {
        return FamiliesResponses.builder()
                .roleFamily(user.getRoleFamily())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday().toString())
                .build();
    }
}
