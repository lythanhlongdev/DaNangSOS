package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.responses.main.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.main.ListHistoryByUserResponses;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IHistoryReadService {

    History getHistoryById(@NotNull Long historyId) throws Exception; //  oke

    List<ListHistoryByUserResponses> getAllHistoryByUser() throws Exception;// user, ok

    List<ListHistoryByRescueStationResponses> getAllHistoryByRescueStation() throws Exception;// sos, ok

    List<ListHistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancelByRescueStation() throws Exception;// sos, ok
    ListHistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception; // sos
}
