package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.responses.main.HistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.main.HistoryByUserResponses;
import com.capstone2.dnsos.responses.main.HistoryInMapUserResponse;
import com.capstone2.dnsos.responses.main.RescueByHistoryResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IHistoryReadService {

    HistoryInMapUserResponse getCurrentHistoryInMapUser(@NotNull Long historyId) throws Exception; //  oke

    List<HistoryByUserResponses> getAllHistoryByUser() throws Exception;// user, ok

    List<HistoryByRescueStationResponses> getAllHistoryByRescueStation() throws Exception;// sos, ok

    List<HistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancel() throws Exception;// sos, ok
    HistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception; // sos
}
