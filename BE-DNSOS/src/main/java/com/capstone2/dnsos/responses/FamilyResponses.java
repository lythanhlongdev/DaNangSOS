package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FamilyResponses {
    private Long familyId;
    private String fullName;
    private String phoneNumber;
    private String roleFamily;
    public static FamilyResponses mapperUser(User user) {
        return FamilyResponses.builder()
                .familyId(user.getFamily().getFamilyId())
                .roleFamily(user.getRoleFamily())
                .fullName(user.getLastName() +" "+user.getFirstName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
