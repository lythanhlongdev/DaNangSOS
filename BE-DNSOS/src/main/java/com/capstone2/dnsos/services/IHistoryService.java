package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.responses.HistoryUserResponses;

import java.util.List;

public interface IHistoryService {
    HistoryUserResponses createHistory(HistoryDTO historyDTO) throws Exception;// user

    void uploadMediaHistory(History history) throws Exception;// user

    History getHistoryById(Long historyId) throws Exception; // sos'

    List<History> getAllHistory() throws Exception; // sos

    boolean updateStatus(Long historyId) throws Exception; // sos'
}

