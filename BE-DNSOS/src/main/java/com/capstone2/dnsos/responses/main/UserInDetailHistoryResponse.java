package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserInDetailHistoryResponse {
    private Long id;
    private String avatar;
    private String passport;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private String address;
    private boolean isActivity;

    public static UserInDetailHistoryResponse mapper(User user) {
        return UserInDetailHistoryResponse.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .isActivity(user.getIsActivity())
                .build();
    }
}
