package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.RescueStationRescueWorker;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RescueWorkerDetailHistoryResponse {

    private String status;
    private Long id;
    private String avatar;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private String createdAt;
    private String updatedAt;
    private String rescueStationName;

    public static RescueWorkerDetailHistoryResponse mapper(RescueStationRescueWorker currentWorker) {
        User user = currentWorker.getRescue().getUser();
        RescueStation rescueStation = currentWorker.getRescueStation();
        return RescueWorkerDetailHistoryResponse.builder()
                .status(currentWorker.isActivity() ? "Còn Làm việc" : "Đã Nghỉ làm")
                .phoneNumber(user.getPhoneNumber())
                .id(currentWorker.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toString())
                .avatar(user.getAvatar() == null ? "" : user.getAvatar())
                .createdAt(currentWorker.getCreatedAt().toString())
                .updatedAt(currentWorker.getUpdatedAt().toString())
                .rescueStationName(rescueStation.getRescueStationsName())
                .build();
    }

}
