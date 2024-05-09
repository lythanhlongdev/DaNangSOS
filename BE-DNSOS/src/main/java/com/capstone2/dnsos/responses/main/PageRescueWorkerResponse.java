package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.*;

import lombok.*;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PageRescueWorkerResponse {

    private String status;
    private String avatar;
    private Long id;
    private String createdAt;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private String rescueStationName;
    private boolean isActivity;

    public static PageRescueWorkerResponse mapper(RescueStationRescueWorker rescueStationRescueWorker) {
        // đây là id định danh ở trong RescueStationRescueWorker không phải ở rescue cẩn thận chỗ này
        Long WorkerId = rescueStationRescueWorker.getId();
        Rescue rescue = rescueStationRescueWorker.getRescue();
        RescueStation rescueStation = rescueStationRescueWorker.getRescueStation();
        User user = rescue.getUser();
        return PageRescueWorkerResponse.builder()
                .status(rescueStationRescueWorker.isActivity() ? "Còn Làm Việc" : "Đã Nghỉ Làm ")
                .createdAt(user.getCreatedAt().toString())
                .avatar(user.getAvatar() == null ? "" : user.getAvatar())
//                .id(rescue.getId())
                .id(WorkerId)
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toString())
                .isActivity(user.getIsActivity())
                .rescueStationName(rescueStation == null ? "" : rescueStation.getRescueStationsName())
                .build();
    }

}
