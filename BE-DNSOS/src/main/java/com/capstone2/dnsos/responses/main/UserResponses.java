package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponses {

    private String createAt;
    private Long id;
    private String phoneNumber;
    private String passport;
    private String firstName;
    private String lastName;
    private String password;
    private String birthday;
    private String address;
    private String roleFamily;
    private boolean isActivity;
    private Set<String> roles;

    public static UserResponses mapper(User user) {
//        Set<String> role = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
        Set<String> role = user.getRoles().stream()
                .map(role1 -> role1.getRoleName().toUpperCase()).collect(Collectors.toSet());

        return UserResponses.builder()
                .createAt(user.getCreatedAt().toString())
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .roleFamily(user.getRoleFamily())
                .isActivity(user.getIsActivity())
                .roles(role)
                .build();
    }
}
