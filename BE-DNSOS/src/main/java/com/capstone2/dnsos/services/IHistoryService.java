package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.HistoryMediaDTO;
import com.capstone2.dnsos.models.History;

public interface IHistoryService {
    History createHistory(HistoryDTO historyDTO) throws Exception;
    History uploadMediaHistory(HistoryMediaDTO historyDTO) throws Exception;
}
