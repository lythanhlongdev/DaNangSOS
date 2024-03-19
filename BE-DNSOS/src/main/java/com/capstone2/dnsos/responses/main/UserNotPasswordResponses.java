package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNotPasswordResponses {

    private String phoneNumber;
    private String passport;
    private String fullName;
    private String birthday;
    private String address;

    public static UserNotPasswordResponses mapper(User user) {
        return UserNotPasswordResponses.builder()
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .build();

    }
}
