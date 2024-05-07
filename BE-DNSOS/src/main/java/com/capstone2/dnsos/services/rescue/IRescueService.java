package com.capstone2.dnsos.services.rescue;

import org.springframework.data.domain.Pageable;

import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.responses.main.PageRescueWorkerResponse;
import com.capstone2.dnsos.responses.main.RescueByHistoryResponse;
import com.capstone2.dnsos.responses.main.RescueResponse;

import jakarta.validation.Valid;

import java.util.List;
public interface IRescueService {

    RescueByHistoryResponse scanQrCode(GpsDTO gpsDTO) throws Exception;

    RescueByHistoryResponse updateGPS(GpsDTO gpsDTO) throws Exception;

    //    RescueResponse register(RegisterDTO registerDTO) throws  Exception;
    RescueResponse register(RegisterDTO registerDTO) throws Exception;
    
    List<PageRescueWorkerResponse> getAllRescueWorker(Pageable page) throws Exception;
    
    void deleteRescueWorker(@Valid Long id)throws Exception;
}
