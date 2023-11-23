package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.HistoryMediaDTO;
import com.capstone2.dnsos.exceptions.DataNotFoundException;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.User;
import com.capstone2.dnsos.repositories.HistoryRepository;
import com.capstone2.dnsos.repositories.RescueStationRepository;
import com.capstone2.dnsos.repositories.UserRepository;
import com.capstone2.dnsos.services.IHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements IHistoryService {
    private  final UserRepository userRepository;
    private  final RescueStationRepository rescueStationRepository;
    private  final HistoryRepository  historyRepository;

    @Override
    public History createHistory(HistoryDTO historyDTO) throws Exception {
        User exstingUser = userRepository
                .findById(historyDTO.getUserId())
                .orElseThrow(()-> new DataNotFoundException("cannot find user not fount id: "+historyDTO.getUserId()));
        // su ly

        return null;
    }

    @Override
    public History uploadMediaHistory(HistoryMediaDTO historyDTO) throws Exception {
        return null;
    }
}
