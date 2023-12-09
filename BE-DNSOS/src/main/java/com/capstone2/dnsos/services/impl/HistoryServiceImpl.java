package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.common.CalculateDistance;
import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.common.ResultKM;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.logs.IHistoryLog;
import com.capstone2.dnsos.models.*;
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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.Arrays;
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
    private final ICancelHistoryRepository cancelHistoryRepository;

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


    @Override
    public ListHistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception {
        return null;
    }


    // page and limit
    @Override
    public List<ListHistoryByUserResponses> getAllHistoryByUser(@NotNull String phoneNumber) throws Exception {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find user with phone number: " + phoneNumber));
        List<History> historiesByUser = historyRepository.findAllByUser(existingUser);
        return historiesByUser.stream().map((history) -> ListHistoryByUserResponses.mapper(history, historyMedia.findByHistory(history))).toList();
    }


    @Override
    public boolean updateHistoryStatusCancelUser(CancelDTO cancelDTO) throws Exception {
        History existingHistory = getHistoryById(cancelDTO.getHistoryId());
        Status status = existingHistory.getStatus();
        if (!existingHistory.getUser().getPhoneNumber().equals(cancelDTO.getPhoneNumber())) {
            throw new InvalidParamException("User not have history with id: " + cancelDTO.getHistoryId());
        } else if (status.getValue() >= 3) {
            throw new InvalidParamException("You cannot cancel because the rescue is in state: " + status);
        }
        History oldHistory = History.builder()
                .historyId(existingHistory.getHistoryId())
                .status(existingHistory.getStatus())
                .latitude(existingHistory.getLatitude())
                .longitude(existingHistory.getLongitude())
                .note(existingHistory.getNote())
                .user(existingHistory.getUser())
                .rescueStation(existingHistory.getRescueStation())
                .build();
        existingHistory.setStatus(Status.CANCELLED_USER);
        existingHistory = historyRepository.save(existingHistory);
        CancelHistory cancelHistory = CancelHistory.builder()
                .history(existingHistory)
                .note(cancelDTO.getNote())
                .role("USER")
                .build();
        cancelHistoryRepository.save(cancelHistory);
        historyLog.onUpdate(oldHistory, existingHistory);
        return true;
    }

    @Override
    public boolean updateHistoryStatusCancel(CancelDTO cancelDTO) throws Exception {
        History existingHistory = getHistoryById(cancelDTO.getHistoryId());
        Status status = existingHistory.getStatus();
        if (!existingHistory.getRescueStation().getPhoneNumber().equals(cancelDTO.getPhoneNumber())) {
            throw new InvalidParamException("Rescue station not have history with id: " + cancelDTO.getHistoryId());
        } else if (status.getValue() >= 3) {
            throw new InvalidParamException("You cannot cancel because the rescue is in state: " + status);
        }
        History oldHistory = History.builder()
                .historyId(existingHistory.getHistoryId())
                .status(existingHistory.getStatus())
                .latitude(existingHistory.getLatitude())
                .longitude(existingHistory.getLongitude())
                .note(existingHistory.getNote())
                .user(existingHistory.getUser())
                .rescueStation(existingHistory.getRescueStation())
                .build();
        existingHistory.setStatus(Status.CANCELLED);
        existingHistory = historyRepository.save(existingHistory);
        CancelHistory cancelHistory = CancelHistory.builder()
                .history(existingHistory)
                .note(cancelDTO.getNote())
                .role("RESCUE_STATION")
                .build();
        cancelHistoryRepository.save(cancelHistory);
        historyLog.onUpdate(oldHistory, existingHistory);
        return true;
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
    public List<ListHistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancelByRescueStation(String phoneNumber) throws Exception {
        RescueStation exitingRescueStation = rescueStationRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with phone number: " + phoneNumber));
        List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED, Status.CANCELLED_USER);
        List<History> historyByRescueStation = historyRepository.findAllByRescueStationAndStatusNotIn(exitingRescueStation, notInStatus);
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

    @Override
    public History updateHistoryGPS(GpsDTO gpsDTO) throws Exception {
        History existingHistory = getHistoryById(gpsDTO.getHistoryId());
        if (!existingHistory.getUser().getPhoneNumber().equals(gpsDTO.getPhoneNumber())) {
            throw new InvalidParamException("User not have history with id: " + gpsDTO.getHistoryId());
        } else if (existingHistory.getStatus().getValue() >= 3) {
            throw new InvalidParameterException("Rescue has ended and stopped updating location, history with id: " + gpsDTO.getHistoryId());
        }
        History oldHistory = History.builder()
                .historyId(existingHistory.getHistoryId())
                .status(existingHistory.getStatus())
                .latitude(existingHistory.getLatitude())
                .longitude(existingHistory.getLongitude())
                .note(existingHistory.getNote())
                .user(existingHistory.getUser())
                .rescueStation(existingHistory.getRescueStation())
                .build();
        double meters = GPS.calculateDistance(gpsDTO.getLatitude(), gpsDTO.getLongitude(), existingHistory.getLatitude(), existingHistory.getLongitude());
        meters = BigDecimal.valueOf(meters).setScale(2, RoundingMode.HALF_UP).doubleValue() * 1000;

//        if (meters >= 50) {
//            existingHistory.setLatitude(gpsDTO.getLatitude());
//            existingHistory.setLongitude(gpsDTO.getLongitude());
//        }

        existingHistory.setLatitude(gpsDTO.getLatitude());
        existingHistory.setLongitude(gpsDTO.getLongitude());
        existingHistory = historyRepository.save(existingHistory);
        historyLog.onUpdate(oldHistory, existingHistory);
        return existingHistory;
    }

    @Override
    public boolean updateHistoryStatusConfirmed(ConfirmedDTO confirmedDTO) throws Exception {
        History existingHistory = getHistoryById(confirmedDTO.getHistoryId());
        boolean checkPhoneNumber = existingHistory.getRescueStation().getPhoneNumber().equals(confirmedDTO.getPhoneNumber());
        boolean checkStatus = existingHistory.getStatus().getValue() == -1;
        if (!checkPhoneNumber) {
            throw new InvalidParamException("Rescue station not have history with id: " + confirmedDTO.getHistoryId());
        } else if (!checkStatus) {
            throw new InvalidParamException("Cannot update to CONFIRMED because the stage has been completed");
        }
        History oldHistory = History.builder()
                .historyId(existingHistory.getHistoryId())
                .status(existingHistory.getStatus())
                .latitude(existingHistory.getLatitude())
                .longitude(existingHistory.getLongitude())
                .note(existingHistory.getNote())
                .user(existingHistory.getUser())
                .rescueStation(existingHistory.getRescueStation())
                .build();
        existingHistory.setStatus(Status.CONFIRMED);
        existingHistory = historyRepository.save(existingHistory);
        historyLog.onUpdate(oldHistory, existingHistory);
        return true;
    }

    @Override
    public boolean updateHistoryStatus(StatusDTO statusDTO) throws Exception {
        History existingHistory = getHistoryById(statusDTO.getHistoryId());
        if (!existingHistory.getRescueStation().getPhoneNumber().equals(statusDTO.getPhoneNumber())) {
            throw new InvalidParamException("Invalid: Rescue station not have history with id: " + statusDTO.getHistoryId());
        }
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
