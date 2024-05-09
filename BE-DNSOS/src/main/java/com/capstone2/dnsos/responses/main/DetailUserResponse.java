package com.capstone2.dnsos.responses.main;


import com.capstone2.dnsos.models.main.Role;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailUserResponse {

    private Long id;
    private String avatar;
    private String phoneNumber;
    private String passport;
    private String fullName;
    private String birthday;
    private String address;
    private String createdAt;
    private List<FamiliesResponses> families;
    private Set<String> roles;

    public static DetailUserResponse mapper(User user, List<User> families) {
        List<FamiliesResponses> familyResponses = families == null ? new ArrayList<>() : families.stream().map(FamiliesResponses::mapperUser).toList();
        Set<String> roles = user.getRoles().stream().map(rl -> rl.getRoleName().toUpperCase()).collect(Collectors.toSet());
        return DetailUserResponse.builder()
                .id(user.getId())
                .avatar(user.getAvatar() == null ? "" : user.getAvatar())
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .fullName(user.getLastName() + " " + user.getFirstName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .families(familyResponses)
                .roles(roles)
                .createdAt(user.getCreatedAt().toString())
                .build();

    }
}
