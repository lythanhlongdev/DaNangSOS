package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.common.CalculateDistance;
import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.common.ResultKM;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.logs.IHistoryLog;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;
import com.capstone2.dnsos.models.RescueStation;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.*;
import com.capstone2.dnsos.responses.HistoryUserResponses;
import com.capstone2.dnsos.services.histories.IHistoryCreateDeleteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryCreateDeleteServiceIml implements IHistoryCreateDeleteService {

    private final IUserRepository userRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryRepository historyRepository;
    private final IHistoryLog historyLog;
    private final IHistoryMediaRepository historyMedia;
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryCreateDeleteServiceIml.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HistoryUserResponses createHistory(HistoryDTO historyDTO) throws Exception {
        LOGGER.info("Creating history for user with id: {}", historyDTO.getUserId());
        // check user
        User existingUser = userRepository.findById(historyDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Cannot find user with id: " + historyDTO.getUserId()));
        // get list rescue station
        List<RescueStation> rescueStationList = rescueStationRepository.findAll();
        // get gps user in historyDTO
        GPS gpsUser = GPS.builder()
                .latitude(historyDTO.getLatitude())
                .longitude(historyDTO.getLongitude())
                .build();
        // list calculate distance
        List<ResultKM> resultKM = CalculateDistance.calculateDistance(gpsUser, rescueStationList);
        // tim km nho nhat
        ResultKM result = resultKM
                .stream()
                .min(Comparator.comparingDouble(ResultKM::getKilometers))
                .orElseThrow(() -> new RuntimeException("No rescue station found"));

        // get id tram gan nhat trong data base len
        RescueStation rescueStation = rescueStationRepository.findById(result.getRescueStationID())
                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with id: " + result.getRescueStationID()));

//        findNearestRescueStation()
        // tao history
        History newHistory = History.builder()
                .user(existingUser)
                .rescueStation(rescueStation)
                .latitude(gpsUser.getLatitude())
                .longitude(gpsUser.getLongitude())
                .note(historyDTO.getNote())
                .status(Status.SYSTEM_RECEIVED)
                .build();
        History history = historyRepository.save(newHistory);
        HistoryMedia media = HistoryMedia.builder().history(history).build();
        historyMedia.save(media);
        historyLog.afCreate(history);
        return HistoryUserResponses.mapperHistoryAndKilometers(history, result);
    }
}
