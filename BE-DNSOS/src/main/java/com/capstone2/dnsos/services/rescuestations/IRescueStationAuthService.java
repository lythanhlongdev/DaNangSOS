package com.capstone2.dnsos.services.rescuestations;

import com.capstone2.dnsos.dto.LoginDTO;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.responses.main.RescueStationResponses2;

public interface IRescueStationAuthService {
    RescueStationResponses2 register(RescueStationDTO rescueStationDTO) throws Exception;

    RescueStationResponses2 getInfoRescue() throws Exception;

    RescueStationResponses2 UpdateInfoRescue(UpdateRescueDTO updateRescueDTO) throws Exception;

}
