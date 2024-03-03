package com.capstone2.dnsos.services.rescuestations;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.models.main.RescueStation;

public interface IRescueStationAuthService {
    RescueStation register(RescueStationDTO rescueStationDTO) throws Exception;

}
