package com.capstone2.dnsos.responses.main;


import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RescueWorkerResponse {

    private String id;
    private String recueStationName;
    private String phoneNumber;
    private String fullName;
    private String gps;

    public static RescueWorkerResponse mapperUserIsRescueWorker(User user) {
        Rescue rescue = user.getRescues();
        RescueStation rescueStation = rescue.getRescueStation();
        return RescueWorkerResponse.builder()
                .id(rescue.getId().toString())
                .recueStationName(rescueStation.getRescueStationsName())
                .phoneNumber(user.getPhoneNumber())
                .gps(String.format("%s, %s", rescue.getLatitude(), rescue.getLongitude()))
                .fullName(String.format("%s %s", user.getLastName(), user.getFirstName()))
                .build();
    }
}
