package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseNotFamily {

    private String phoneNumber;
    private String passport;
    private String fullName;
    private String birthday;
    private String address;

    public static UserResponseNotFamily mapper(User user) {
        return UserResponseNotFamily.builder()
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .build();
    }
}
