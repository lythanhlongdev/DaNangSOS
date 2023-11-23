package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyResponses {

    @JsonProperty("family_id")
    private Long familyId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("role_family")
    private String roleFamily;
    public static FamilyResponses mapperUser(User user) {
        return FamilyResponses.builder()
                .familyId(user.getFamilyId())
                .roleFamily(user.getRoleFamily())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
