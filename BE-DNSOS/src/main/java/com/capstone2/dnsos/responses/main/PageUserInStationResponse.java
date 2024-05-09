package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageUserInStationResponse {

    private Long id;
    private String avatar;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private String roleFamily;
    private boolean isActivity;
    private Set<String> roles;
    public static PageUserInStationResponse mapper(User user) {
        Set<String> roles = user.getRoles().stream().map(rl -> rl.getRoleName().toUpperCase()).collect(Collectors.toSet());
        return PageUserInStationResponse.builder()
                .avatar(user.getAvatar()==null ? "":user.getAvatar())
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toString())
                .roleFamily(user.getRoleFamily())
                .isActivity(user.getIsActivity())
                .roles(roles)
                .build();
    }
}
