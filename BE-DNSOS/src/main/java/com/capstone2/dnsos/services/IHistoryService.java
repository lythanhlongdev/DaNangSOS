package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.responses.HistoryUserResponses;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IHistoryService {
    HistoryUserResponses createHistory(HistoryDTO historyDTO) throws Exception;// user

    void uploadMediaHistory(History history) throws Exception;// user

    List<ListHistoryByUserResponses> getHistoriesByUserId(Long userId) throws Exception;

    boolean updateStatusHistoryById(StatusDTO statusDTO) throws Exception; // sos'

    History getHistoryById(@NotNull Long historyId) throws Exception; // admin'

    List<History> getAllHistoryByRescueStationId(Long id) throws Exception;

    List<History> getAllHistory() throws Exception; // admin

}

