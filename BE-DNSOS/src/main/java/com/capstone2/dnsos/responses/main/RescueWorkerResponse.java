package com.capstone2.dnsos.responses.main;


import com.capstone2.dnsos.models.main.HistoryRescue;
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
    private String rescueStationName;
    private String phoneNumber;
    private String fullName;
    private String rescueGPS;

    public static RescueWorkerResponse mapperUserIsRescueWorker(HistoryRescue historyRescue) {
        Rescue rescue = historyRescue.getRescue();
        User user = rescue.getUser();
        RescueStation rescueStation = historyRescue.getHistory().getRescueStation();
        return RescueWorkerResponse.builder()
                .id(historyRescue.getRescue().getId().toString())
                .rescueStationName(rescueStation.getRescueStationsName())
                .phoneNumber(user.getPhoneNumber())
                .rescueGPS(String.format("%s, %s", historyRescue.getRescue().getLatitude(), historyRescue.getRescue().getLongitude()))
                .fullName(String.format("%s %s", user.getLastName(), user.getFirstName()))
                .build();
    }

    public static RescueWorkerResponse mapperUserIsRescueWorker(Rescue rescue, RescueStation rescueStation) {
        User user = rescue.getUser();
        return RescueWorkerResponse.builder()
                .id(rescue.getId().toString())
                .rescueStationName(rescueStation.getRescueStationsName())
                .phoneNumber(user.getPhoneNumber())
                .rescueGPS(String.format("%s, %s", rescue.getLatitude(), rescue.getLongitude()))
                .fullName(String.format("%s %s", user.getLastName(), user.getFirstName()))
                .build();
    }
}
