package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.logs.IHistoryLog;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.RescueStation;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.*;
import com.capstone2.dnsos.responses.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import com.capstone2.dnsos.services.histories.IHistoryReadService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryReadServiceIml implements IHistoryReadService {

    private final IUserRepository userRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IHistoryRepository historyRepository;
    private final IHistoryMediaRepository historyMedia;


    @Override
    public List<ListHistoryByUserResponses> getAllHistoryByUser(@NotNull String phoneNumber) throws Exception {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find user with phone number: " + phoneNumber));
        List<History> historiesByUser = historyRepository.findAllByUser(existingUser);
        return historiesByUser.stream().map((history) -> ListHistoryByUserResponses.mapper(history, historyMedia.findByHistory(history))).toList();
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
    public ListHistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception {
        return null;
    }

}
