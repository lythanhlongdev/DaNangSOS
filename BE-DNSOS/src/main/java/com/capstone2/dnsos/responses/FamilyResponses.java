package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.User;
import lombok.*;
import org.modelmapper.ModelMapper;

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
                .familyId(user.getFamilyId().getFamilyId())
                .roleFamily(user.getRoleFamily())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
