package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FamilyResponses {

    private String roleFamily;
    private String fullName;
    private String phoneNumber;
    private LocalDate birthday;

    public static FamilyResponses mapperUser(User user) {
        return FamilyResponses.builder()
                .roleFamily(user.getRoleFamily())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .build();
    }
}
