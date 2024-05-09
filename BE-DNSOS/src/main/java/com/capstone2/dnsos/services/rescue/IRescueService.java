package com.capstone2.dnsos.services.rescue;

import com.capstone2.dnsos.responses.main.DetailRescueWorkerResponse;
import org.springframework.data.domain.Page;
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

    Page<PageRescueWorkerResponse> getAllRescueWorker(Pageable page) throws Exception;

    DetailRescueWorkerResponse getDetailRescueWorkerById(Long workerId) throws Exception;

    Page<PageRescueWorkerResponse> getAllRescueWorkerForAdmin(Pageable page) throws Exception;

    DetailRescueWorkerResponse getDetailRescueWorkerByIdForAdmin(Long workerId) throws Exception;

    void deleteRescueWorker(@Valid Long id) throws Exception;
}
