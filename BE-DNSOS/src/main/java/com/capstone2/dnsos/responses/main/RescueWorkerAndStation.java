package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.RescueStationRescueWorker;
import com.capstone2.dnsos.models.main.User;
import lombok.*;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RescueWorkerAndStation {
    private User user;
    private RescueStation rescueStation;

}
