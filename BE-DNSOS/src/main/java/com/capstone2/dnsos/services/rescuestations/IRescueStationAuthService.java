package com.capstone2.dnsos.services.rescuestations;

import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.responses.main.RescueForAdminResponses;
import com.capstone2.dnsos.responses.main.RescueStationResponses;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRescueStationAuthService {
    RescueStationResponses register(RescueStationDTO rescueStationDTO) throws Exception;

    RescueStationResponses getInfoRescue() throws Exception;

    List<RescueForAdminResponses> getAllRecue(Pageable pageable) throws  Exception;
    RescueStationResponses UpdateInfoRescue(UpdateRescueDTO updateRescueDTO) throws Exception;


}
