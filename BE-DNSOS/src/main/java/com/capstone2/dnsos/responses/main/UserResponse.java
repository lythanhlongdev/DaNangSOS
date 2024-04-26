package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String avatar;
    private String phoneNumber;
    private String passport;
    private String fullName;
    private String birthday;
    private String address;

    public static UserResponse mapper(User user) {
        return UserResponse.builder()
                .avatar(user.getAvatar())
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .build();
    }
}
