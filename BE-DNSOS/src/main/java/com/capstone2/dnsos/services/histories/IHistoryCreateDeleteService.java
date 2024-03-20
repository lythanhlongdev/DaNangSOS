package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.responses.main.CreateHistoryByUserResponses;

public interface IHistoryCreateDeleteService {
    CreateHistoryByUserResponses createHistory(HistoryDTO historyDTO) throws Exception;// user, ok
}
