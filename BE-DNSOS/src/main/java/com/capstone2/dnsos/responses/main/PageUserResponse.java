package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageUserResponse {

    private Long id;
    private String avatar;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private String roleFamily;
    private boolean isActivity;

    public static PageUserResponse mapper(User user) {
        return PageUserResponse.builder()
                .avatar(user.getAvatar()==null ? "":user.getAvatar())
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toString())
                .roleFamily(user.getRoleFamily())
                .isActivity(user.getIsActivity())
                .build();
    }
}
