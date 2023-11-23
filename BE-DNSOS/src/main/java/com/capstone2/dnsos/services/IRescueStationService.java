package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.models.RescueStation;

public interface IRescueStationService {
    RescueStation register(RescueStationDTO rescueStationDTO)throws  Exception;
    String login(LoginDTO loginDTO) throws Exception;
}
