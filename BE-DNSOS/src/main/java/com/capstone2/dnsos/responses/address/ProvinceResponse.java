package com.capstone2.dnsos.responses.address;

import com.capstone2.dnsos.models.address.Province;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceResponse {

    private String code;
    private String fullName;


    public static ProvinceResponse mapperEntity(Province province) {
        return builder()
                .code(province.getCode())
                .fullName(province.getFullName())
                .build();
    }
}
