package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.logs.IHistoryLog;
import com.capstone2.dnsos.models.CancelHistory;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.repositories.*;
import com.capstone2.dnsos.services.histories.IHistoryUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;

@RequiredArgsConstructor
@Service
public class HistoryUpdateServiceIml implements IHistoryUpdateService {

    private final IHistoryRepository historyRepository;
    private final IHistoryLog historyLog;
    private final ICancelHistoryRepository cancelHistoryRepository;

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


    public History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }
}
