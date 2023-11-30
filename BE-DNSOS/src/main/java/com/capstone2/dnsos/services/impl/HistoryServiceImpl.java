package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.common.CalculateDistance;
import com.capstone2.dnsos.common.GPS;
import com.capstone2.dnsos.common.ResultKM;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.RescueStation;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.HistoryRepository;
import com.capstone2.dnsos.repositories.RescueStationRepository;
import com.capstone2.dnsos.repositories.UserRepository;
import com.capstone2.dnsos.responses.HistoryUserResponses;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import com.capstone2.dnsos.services.IHistoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;

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

        // tao history
        History newHistory = History.builder()
                .user(existingUser)
                .rescueStation(rescueStation)
                .latitude(gpsUser.getLatitude())
                .longitude(gpsUser.getLongitude())
                .note(historyDTO.getNote())
                .status(Status.SYSTEM_RECEIVED)
                .image1(" ")
                .image2(" ")
                .image3(" ")
                .voice(" ")
                .build();
        return HistoryUserResponses.mapperHistoryAndKilometers(historyRepository.save(newHistory), result);
    }

    // page and limit
    @Override
    public List<ListHistoryByUserResponses> getHistoriesByUserId(@NotNull Long userId) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Cannot find user with id: " + userId));
        List<History> historiesByUser = historyRepository.findAllByUser(existingUser);
        return historiesByUser.stream().map(ListHistoryByUserResponses::mapper).toList();
    }
    @Override
    public List<History> getAllHistoryByRescueStationId(Long id) throws Exception {
        RescueStation existingRescueStation = rescueStationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find Rescue Station with id: " + id));
        List<History> historiesByRescueStation = historyRepository.findAllByRescueStation(existingRescueStation);

        return null;
    }

    @Override
    public boolean updateStatusHistoryById(StatusDTO statusDTO) throws Exception {
        Long historyId = statusDTO.getHistoryId();
        History existingHistory = historyRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find history with id: " + historyId));
        int status = statusDTO.getStatus();
        return switch (status) {
            case 1 -> {
                existingHistory.setStatus(Status.ON_THE_WAY);
                historyRepository.save(existingHistory);
                yield true;
            }
            case 2 -> {
                existingHistory.setStatus(Status.ARRIVED);
                historyRepository.save(existingHistory);
                yield true;
            }
            case 3 -> {
                existingHistory.setStatus(Status.COMPLETED);
                historyRepository.save(existingHistory);
                yield true;
            }
            default -> false;
        };
    }

    @Override
    public void uploadMediaHistory(History history) {
        historyRepository.save(history);
    }



    @Override
    public History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }

    @Override
    public List<History> getAllHistory() throws Exception {
        return historyRepository.findAll();
    }

}
