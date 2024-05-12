package com.capstone2.dnsos.services.rescuestations;

import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.dto.UpdateRescueForAdminDTO;
import com.capstone2.dnsos.responses.main.AvatarResponse;
import com.capstone2.dnsos.responses.main.DetailRescueStationResponse;
import com.capstone2.dnsos.responses.main.PageRescueResponse;
import com.capstone2.dnsos.responses.main.RescueStationResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IRescueStationAuthService {

    RescueStationResponses register(RescueStationDTO rescueStationDTO) throws Exception;

    RescueStationResponses getInfoRescue() throws Exception;

    Page<PageRescueResponse> getAllRescueStation(Pageable pageable) throws Exception;

    //    RescueForAdminResponses
    DetailRescueStationResponse getDetailRescueStationById(Long id) throws Exception;

    RescueStationResponses UpdateInfoRescue(UpdateRescueDTO updateRescueDTO) throws Exception;

    RescueStationResponses UpdateInfoRescueForAdmin(UpdateRescueForAdminDTO updateRescueDTO) throws Exception;

    RescueStationResponses updateStatus(Long rescueStationId, int statusId) throws Exception;

    RescueStationResponses updateAvatar(MultipartFile avatar) throws Exception;

    AvatarResponse getAvatar() throws Exception;
}
