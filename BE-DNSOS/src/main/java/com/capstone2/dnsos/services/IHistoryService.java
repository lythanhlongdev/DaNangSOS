package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.models.History;

import com.capstone2.dnsos.responses.HistoryUserResponses;
import com.capstone2.dnsos.responses.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IHistoryService {
    HistoryUserResponses createHistory(HistoryDTO historyDTO) throws Exception;// user, ok
    List<ListHistoryByUserResponses> getAllHistoryByUser(String phoneNumber) throws Exception;// user, ok
    List<ListHistoryByRescueStationResponses> getAllHistoryByRescueStation(String phoneNumber) throws Exception;// user, ok
    boolean updateStatusHistory(StatusDTO statusDTO) throws Exception; // sos' , ok
    History getHistoryById(@NotNull Long historyId) throws Exception;

}

