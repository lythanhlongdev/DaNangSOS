package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.CancelHistory;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.HistoryUserResponses;
import com.capstone2.dnsos.repositories.main.ICancelHistoryRepository;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.services.histories.IHistoryChangeLogService;
import com.capstone2.dnsos.services.histories.IHistoryUpdateService;
import com.capstone2.dnsos.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class HistoryUpdateServiceIml implements IHistoryUpdateService {

    private final IHistoryRepository historyRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryChangeLogService historyChangeLogService;
    private final ICancelHistoryRepository cancelHistoryRepository;
    private final IHistoryChangeLogService changeLogService;
    private static final String UPDATE = "UPDATE";
    private static final String UPDATE_STATION = "UPDATE_STATION";
    private static final String PATH = "./data";

    @Override
    public Status updateHistoryStatus(StatusDTO statusDTO) throws Exception {

        User loadUserInAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        History existingHistory = getHistoryById(statusDTO.getHistoryId());
        // Hieu nang doan nay => Set Lazy or EGGE in history
        String phoneNumber = existingHistory.getRescueStation().getUser().getPhoneNumber();
        int checkStatus = existingHistory.getStatus().getValue();
        if (!phoneNumber.equals(loadUserInAuth.getPhoneNumber())) {
            throw new InvalidParamException("Invalid: Rescue station not have history with id: " + statusDTO.getHistoryId());
        } else if (checkStatus == Status.COMPLETED.getValue() || checkStatus == Status.CANCELLED_USER.getValue()) {
            throw new InvalidParamException("Cannot update history because has been: " + existingHistory.getStatus());
        }
        Status newStatus = getStatus(statusDTO, existingHistory);
        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);
        existingHistory.setStatus(newStatus);// it update in cache leve 1 but not save in database
        existingHistory = historyRepository.save(existingHistory);
//        historyChangeLogService.updateLog(oldHistory, existingHistory, UPDATE);// save log
        return existingHistory.getStatus();
    }


    private static Status getStatus(StatusDTO statusDTO, History existingHistory) throws InvalidParamException {
//        if (existingHistory.getStatus().getValue() == -1) {
//            throw new InvalidParamException("The history status cannot be updated because the lifeguard station has not been confirmed.");
//        }

        int status = statusDTO.getStatus();
        // Bug: if we are change the number in status
        if (status < 1 || status > 4) {
            throw new InvalidParamException("Invalid status value");
        }
        // 1 -> 4 but enum start 1 we can status  = SYSTEM_RECEIVED, ON_THE_WAY, ARRIVED, COMPLETED
        Status newStatus = Status.values()[status];

        if (existingHistory.getStatus().getValue() >= newStatus.getValue()) {
            throw new InvalidParamException("Cannot update to " + newStatus + " because the stage has been completed");
        }
        return newStatus;
    }

    @Override
    public boolean updateHistoryStatusConfirmed(ConfirmedDTO confirmedDTO) throws Exception {
        History existingHistory = getHistoryById(confirmedDTO.getHistoryId());
        // Hieu nang doan nay => Set Lazy or EGGE in history
        String phoneNumber = existingHistory.getRescueStation().getUser().getPhoneNumber();

        boolean checkPhoneNumber = phoneNumber.equals(confirmedDTO.getRescuePhoneNumber());

        boolean checkStatus = existingHistory.getStatus().getValue() == -1;
        if (!checkPhoneNumber) {
            throw new InvalidParamException("Rescue station not have history with id: " + confirmedDTO.getHistoryId());
        } else if (!checkStatus) {
            throw new InvalidParamException("Cannot update to CONFIRMED because the stage has been completed");
        }
        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);
        existingHistory.setStatus(Status.CONFIRMED);
        existingHistory = historyRepository.save(existingHistory);
        historyChangeLogService.updateLog(oldHistory, existingHistory, UPDATE);
        return true;
    }


    @Override
    public void updateHistoryStatusCancelUser(CancelDTO cancelDTO) throws Exception {
        User loadUserInAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        History existingHistory = this.getHistoryById(cancelDTO.getHistoryId());
        Status status = existingHistory.getStatus();

        if (!existingHistory.getUser().getPhoneNumber().equals(loadUserInAuth.getPhoneNumber())) {
            throw new InvalidParamException("User not have history with id: " + cancelDTO.getHistoryId());
        } else if (status.getValue() >= 3) {
            throw new InvalidParamException("You cannot cancel because the rescue is in state: " + status);
        }
        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);
        existingHistory.setStatus(Status.CANCELLED_USER);
        existingHistory = historyRepository.save(existingHistory);
        CancelHistory cancelHistory = CancelHistory.builder()
                .history(existingHistory)
                .note(cancelDTO.getNote())
                .role("USER")
                .build();
        cancelHistoryRepository.save(cancelHistory);
        historyChangeLogService.updateLog(oldHistory, existingHistory, UPDATE);
    }

//    @Override
//    public boolean updateHistoryStatusCancel(CancelDTO cancelDTO) throws Exception {
//        History existingHistory = getHistoryById(cancelDTO.getId());
//        Status status = existingHistory.getStatus();
//        // Hieu nang doan nay => Set Lazy or EGGE in history
//        String phoneNumber = existingHistory.getRescueStation().getUser().getPhoneNumber();
//        if (phoneNumber.equals(cancelDTO.getUserPhoneNumber())) {
//            throw new InvalidParamException("Rescue station not have history with id: " + cancelDTO.getId());
//        } else if (status.getValue() >= 3) {
//            throw new InvalidParamException("You cannot cancel because the rescue is in state: " + status);
//        }
//        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);
//        existingHistory.setStatus(Status.CANCELLED);
//        existingHistory = historyRepository.save(existingHistory);
//        CancelHistory cancelHistory = CancelHistory.builder()
//                .history(existingHistory)
//                .note(cancelDTO.getNote())
//                .role("RESCUE_STATION")
//                .build();
//        cancelHistoryRepository.save(cancelHistory);
//        historyChangeLogService.updateLog(oldHistory, existingHistory, UPDATE);
//        return true;
//    }

    @Override
    public HistoryUserResponses changeRescueStation(Long historyId) throws Exception {
        History history = getHistoryById(historyId);
        if(history.getStatus().getValue() != -1)
        {
            throw new InvalidParameterException("This is "+ history.getStatus().toString()+ ", cannot change station!");
        }
        String filename = history.getId().toString();
        Long stationId = history.getRescueStation().getId();
        // index of current station
        int currentIndex = -1;
        // get all KilometerMin Objects from file path = " ./data/? "
        List<KilometerMin> listStation = FileUtil.readFromFile("./data",filename);
        // the size of the list
        int numberOfStation = listStation.size();
        // Sort in ascending
        Collections.sort(listStation, new Comparator<KilometerMin>() {
            @Override
            public int compare(KilometerMin o1, KilometerMin o2) {
                return o1.getKilometers().compareTo(o2.getKilometers());
            }
        });

        for(KilometerMin currentStation: listStation)
        {
            if(currentStation.getRescueStationID() == stationId)
            {
                currentIndex = listStation.indexOf(currentStation);
                currentIndex++;
                if(currentIndex > (numberOfStation - 1))
                {
                    currentIndex = 0;
                }
                KilometerMin newStation = listStation.get(currentIndex);
                RescueStation newRescueStation = rescueStationRepository.findById(newStation.getRescueStationID())
                        .orElseThrow(() -> new NotFoundException("Cannot find rescue station with id: " + newStation.getRescueStationID()));

                history.setRescueStation(newRescueStation);
                historyRepository.save(history); // Consider, should return boolean or new Rescue Station
                changeLogService.createLog(history,UPDATE_STATION);

                // return current station
                HistoryUserResponses nStation = HistoryUserResponses.mapperHistoryAndKilometers(history,newStation);
                return nStation;

            }
        }

        return null;
    }

    private User loadUserInAuth()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @Override
    public History updateHistoryGPS(GpsDTO gpsDTO) throws Exception {
        History existingHistory = getHistoryById(gpsDTO.getHistoryId());
        double latitude = gpsDTO.getLatitude();
        double longitude = gpsDTO.getLongitude();
        String phoneNumber = loadUserInAuth().getPhoneNumber();

        if (!existingHistory.getUser().getPhoneNumber().equals(phoneNumber)) {
            throw new InvalidParamException("User not have history with id: " + gpsDTO.getHistoryId());
        } else if (existingHistory.getStatus().getValue() >= 3) {
            throw new InvalidParameterException("Rescue has ended and stopped updating location, history with id: " + gpsDTO.getHistoryId());
        }
        History oldHistory = Mappers.getMappers().mapperHistory(existingHistory);

//        double meters = GPS.calculateDistance(latitude, longitude, existingHistory.getLatitude(), existingHistory.getLongitude());
//        meters = BigDecimal.valueOf(meters).setScale(2, RoundingMode.HALF_UP).doubleValue() * 1000;
//        if (meters >= 50) {
//            existingHistory.setLatitude(gpsDTO.getLatitude());
//            existingHistory.setLongitude(gpsDTO.getLongitude());
//        }
        existingHistory.setLatitude(latitude);
        existingHistory.setLongitude(longitude);
        existingHistory = historyRepository.save(existingHistory);
        historyChangeLogService.updateLog(oldHistory, existingHistory, "UPDATE");
        return existingHistory;
    }


    private History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }
}
