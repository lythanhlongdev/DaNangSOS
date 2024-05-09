package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.RescueStation;
import lombok.*;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PageRescueResponse {

    private Long id;
    private String avatar;
    private String createdAt;
    private String rescueStationsName;
    private String phoneNumber;
    private String address;
    private boolean isActivity;
    private String status;

    public static PageRescueResponse mapFromEntity(RescueStation rescueStation) {
        return PageRescueResponse.builder()
                .avatar(rescueStation.getAvatar() == null ? "" : rescueStation.getAvatar())
                .createdAt(rescueStation.getCreatedAt().toString())
                .id(rescueStation.getId())
                .rescueStationsName(rescueStation.getRescueStationsName())
                .phoneNumber(rescueStation.getPhoneNumber1())
                .address(rescueStation.getAddress())
                .isActivity(rescueStation.getIsActivity())
                .status(rescueStation.getStatus().toString())
                .build();
    }
}
