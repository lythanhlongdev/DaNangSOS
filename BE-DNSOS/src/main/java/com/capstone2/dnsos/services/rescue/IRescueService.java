package com.capstone2.dnsos.services.rescue;

import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.models.main.Rescue;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.responses.main.RescueByHistoryResponse;
import com.capstone2.dnsos.responses.main.RescueResponse;

public interface IRescueService {

    RescueByHistoryResponse getRescueByUserId(GpsDTO gpsDTO) throws Exception;

    Rescue updateGPS(GpsDTO gpsDTO) throws Exception;

    //    RescueResponse register(RegisterDTO registerDTO) throws  Exception;
    RescueResponse register(RegisterDTO registerDTO) throws Exception;
}
