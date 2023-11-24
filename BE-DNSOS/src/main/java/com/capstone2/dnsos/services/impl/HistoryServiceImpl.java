package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.common.CalculateDistance;
import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.common.ResultKM;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.DataNotFoundException;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.RescueStation;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.HistoryRepository;
import com.capstone2.dnsos.repositories.RescueStationRepository;
import com.capstone2.dnsos.repositories.UserRepository;
import com.capstone2.dnsos.responses.HistoryUserResponses;
import com.capstone2.dnsos.services.IHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements IHistoryService {
    private final UserRepository userRepository;
    private final RescueStationRepository rescueStationRepository;
    private final HistoryRepository historyRepository;

    @Override
    public HistoryUserResponses createHistory(HistoryDTO historyDTO) throws Exception {
        // check user
        User existingUser = userRepository.findById(historyDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + historyDTO.getUserId()));

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
        ResultKM result = CalculateDistance.calculateDistance(gpsUser, rescueStationList)
                .stream()
                .min(Comparator.comparingDouble(ResultKM::getKilometers))
                .orElseThrow(() -> new RuntimeException("No rescue station found"));

        // get id tram gan nhat trong data base len
        RescueStation rescueStation = rescueStationRepository.findById(result.getRescueStationID())
                .orElseThrow(() -> new DataNotFoundException("Cannot find rescue station with id: " + result.getRescueStationID()));

        // tao history
        History newHistory = History.builder()
                .user(existingUser)
                .rescueStation(rescueStation)
                .latitude(gpsUser.getLatitude())
                .longitude(gpsUser.getLongitude())
                .note(historyDTO.getNote())
                .status(Status.RECEIVED)
                .image1(" ")
                .image2(" ")
                .image3(" ")
                .voice(" ")
                .build();
        return HistoryUserResponses.mapperHistoryAndKilometers(historyRepository.save(newHistory), result);
    }

    @Override
    public void uploadMediaHistory(History history) throws Exception {
         if (historyRepository.save(history) == null){ throw new Exception("Upload media false");}
    }

    @Override
    public History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find History with id: " + historyId));
    }
}
