package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.common.CalculateDistance;
import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.common.ResultKM;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.logs.IHistoryLog;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;
import com.capstone2.dnsos.models.RescueStation;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.*;
import com.capstone2.dnsos.responses.HistoryUserResponses;
import com.capstone2.dnsos.responses.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import com.capstone2.dnsos.services.IHistoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements IHistoryService {
    private final IUserRepository userRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryRepository historyRepository;
    private final IHistoryLog historyLog;
    private final IHistoryMediaRepository historyMedia;

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryServiceImpl.class);


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

    //   chua toi uu
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public HistoryUserResponses createHistory(HistoryDTO historyDTO) throws Exception {
//        LOGGER.info("Creating history for user with id: {}", historyDTO.getUserId());
//        // check user
//        User existingUser = userRepository.findById(historyDTO.getUserId())
//                .orElseThrow(() -> new NotFoundException("Cannot find user with id: " + historyDTO.getUserId()));
//        // get list rescue station
//        List<RescueStation> rescueStationList = rescueStationRepository.findAll();
//        // get gps user in historyDTO
//        GPS gpsUser = GPS.builder()
//                .latitude(historyDTO.getLatitude())
//                .longitude(historyDTO.getLongitude())
//                .build();
//        // list calculate distance
//        List<ResultKM> resultKM = CalculateDistance.calculateDistance(gpsUser, rescueStationList);
//        // tim km nho nhat
//        ResultKM result = resultKM
//                .stream()
//                .min(Comparator.comparingDouble(ResultKM::getKilometers))
//                .orElseThrow(() -> new RuntimeException("No rescue station found"));
//
//        // get id tram gan nhat trong data base len
//        RescueStation rescueStation = rescueStationRepository.findById(result.getRescueStationID())
//                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with id: " + result.getRescueStationID()));
//
////        findNearestRescueStation()
//        // tao history
//        History newHistory = History.builder()
//                .user(existingUser)
//                .rescueStation(rescueStation)
//                .latitude(gpsUser.getLatitude())
//                .longitude(gpsUser.getLongitude())
//                .note(historyDTO.getNote())
//                .status(Status.SYSTEM_RECEIVED)
//                .build();
//        History history = historyRepository.save(newHistory);
//        // save log
//        HistoryLog historyLog = HistoryLog.builder()
//                .history(history)
//                .changeTime(LocalDateTime.now())
//                .status(history.getStatus())
//                .build();
//        historyLogRepository.save(historyLog);
//        return HistoryUserResponses.mapperHistoryAndKilometers(history, result);
//    }


//    private RescueStation findNearestRescueStation(GPS gpsUser) throws Exception {
//        List<RescueStation> rescueStationList = rescueStationRepository.findAll();
//        List<ResultKM> resultKM = CalculateDistance.calculateDistance(gpsUser, rescueStationList);
//        ResultKM result = resultKM
//                .stream()
//                .min(Comparator.comparingDouble(ResultKM::getKilometers))
//                .orElseThrow(() -> new RuntimeException("No rescue station found"));
//        return rescueStationRepository.findById(result.getRescueStationID())
//                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with id: " + result.getRescueStationID()));
//    }


    // page and limit
    @Override
    public List<ListHistoryByUserResponses> getAllHistoryByUser(@NotNull String phoneNumber) throws Exception {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find user with phone number: " + phoneNumber));
        List<History> historiesByUser = historyRepository.findAllByUser(existingUser);
        return historiesByUser.stream().map((history)-> ListHistoryByUserResponses.mapper(history,historyMedia.findByHistory(history))).toList();
    }


    @Override
    public List<ListHistoryByRescueStationResponses> getAllHistoryByRescueStation(String phoneNumber) throws Exception {
        RescueStation exitingRescueStation = rescueStationRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with phone number: " + phoneNumber));
        List<History> historyByRescueStation = historyRepository.findAllByRescueStation(exitingRescueStation);
        return historyByRescueStation
                .stream()
                .map((history) -> ListHistoryByRescueStationResponses
                        .mapper(history,
                                historyMedia.findByHistory(history),
                                userRepository.findByFamily(history.getUser().getFamily())))
                .toList();
    }


    @Override
    public History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }

    //    Bug
    @Override
    public boolean updateStatusHistory(StatusDTO statusDTO) throws Exception {
        Long historyId = statusDTO.getHistoryId();
        History existingHistory = historyRepository.findById(historyId).orElseThrow(() -> new NotFoundException("Cannot find history with id: " + historyId));
        Status newStatus = getStatus(statusDTO, existingHistory);
        History oldHistory = History.builder()
                .historyId(existingHistory.getHistoryId())
                .status(existingHistory.getStatus())
                .latitude(existingHistory.getLatitude())
                .longitude(existingHistory.getLongitude())
                .note(existingHistory.getNote())
                .user(existingHistory.getUser())
                .rescueStation(existingHistory.getRescueStation())
                .build();

        existingHistory.setStatus(newStatus);// it update in cache leve 1 but not save in database
        historyLog.onUpdate(oldHistory, existingHistory);
        existingHistory = historyRepository.save(existingHistory);
        return true;
    }

    private static Status getStatus(StatusDTO statusDTO, History existingHistory) throws InvalidParamException {
        if (existingHistory.getStatus().getValue() == -1) {
            throw new InvalidParamException("The history status cannot be updated because the lifeguard station has not been confirmed.");
        }

        int status = statusDTO.getStatus();
        // Bug: if we are change the number in status
        if (status < 1 || status > 3) {
            throw new InvalidParamException("Invalid status value");
        }
        // 1 -> 3 but enum start 0 we can status + 1 = ON_THE_WAY, ARRIVED, COMPLETED
        Status newStatus = Status.values()[status + 1];

        if (existingHistory.getStatus().getValue() >= newStatus.getValue()) {
            throw new InvalidParamException("Cannot update to " + newStatus + " because the stage has been completed");
        }
        return newStatus;
    }




}
