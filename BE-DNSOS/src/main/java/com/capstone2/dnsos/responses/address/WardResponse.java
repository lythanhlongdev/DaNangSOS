package com.capstone2.dnsos.responses.address;


import com.capstone2.dnsos.models.address.District;
import com.capstone2.dnsos.models.address.Ward;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WardResponse {

    private String code;
    private String fullName;

    public static WardResponse mapperEntity(Ward ward) {
        return WardResponse.builder().code(ward.getCode()).fullName(ward.getFullName()).build();
    }
}
