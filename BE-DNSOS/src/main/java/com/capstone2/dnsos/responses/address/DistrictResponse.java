package com.capstone2.dnsos.responses.address;


import com.capstone2.dnsos.models.address.District;
import lombok.*;


@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictResponse {

    private String code;
    private String fullName;
    public static DistrictResponse mapperEntity(District district) {
        return DistrictResponse.builder()
                .code(district.getCode())
                .fullName(district.getFullName())
                .build();
    }
}
