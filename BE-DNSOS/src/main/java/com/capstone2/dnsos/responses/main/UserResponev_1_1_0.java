package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponev_1_1_0 {

    private String phoneNumber;
    private String passport;
    private String fullName;
    private String birthday;
    private String address;

    public static UserResponev_1_1_0 mapper(User user) {
        return UserResponev_1_1_0.builder()
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .build();
    }
}
