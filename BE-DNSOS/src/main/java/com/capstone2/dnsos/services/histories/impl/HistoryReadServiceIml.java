package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;
import com.capstone2.dnsos.repositories.main.IHistoryMediaRepository;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.HistoryMediaResponses;
import com.capstone2.dnsos.responses.main.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.main.ListHistoryByUserResponses;
import com.capstone2.dnsos.responses.main.UserResponses;
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
        return historiesByUser.stream().map((history) -> ListHistoryByUserResponses.mapFromEntities(history, historyMedia.findByHistory(history))).toList();
    }


    @Override
    public List<ListHistoryByRescueStationResponses> getAllHistoryByRescueStation(String phoneNumber) throws NotFoundException {
        RescueStation rescueStation = rescueStationRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with phone number: " + phoneNumber));
        List<History> histories = historyRepository.findAllByRescueStation(rescueStation);
        return histories.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ListHistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancelByRescueStation(String phoneNumber) throws NotFoundException {
        RescueStation rescueStation = rescueStationRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find rescue station with phone number: " + phoneNumber));

        List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED, Status.CANCELLED_USER);
        List<History> histories = historyRepository.findAllByRescueStationAndStatusNotIn(rescueStation, notInStatus);

        return histories.stream().map(this::mapToResponse).toList();
    }
    private ListHistoryByRescueStationResponses mapToResponse(History history) {
        HistoryMedia medias = historyMedia.findByHistory(history);
        List<User> families = userRepository.findByFamily(history.getUser().getFamily());
        return ListHistoryByRescueStationResponses.builder()
                .status(history.getStatus())
                .historyId(history.getHistoryId())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .note(history.getNote())
                .createdAt(history.getCreatedAt())
                .updatedAt(history.getUpdatedAt())
                .userResponses(UserResponses.mapper(history.getUser(), families))
                .mediaResponses(HistoryMediaResponses.mapFromEntity(medias))
                .build();
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
