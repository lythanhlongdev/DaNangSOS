package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.responses.ListHistoryByRescueStationResponses;
import com.capstone2.dnsos.responses.ListHistoryByUserResponses;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IHistoryReadService {

    History getHistoryById(@NotNull Long historyId) throws Exception; //  oke

    List<ListHistoryByUserResponses> getAllHistoryByUser(String phoneNumber) throws Exception;// user, ok

    List<ListHistoryByRescueStationResponses> getAllHistoryByRescueStation(String phoneNumber) throws Exception;// sos, ok

    List<ListHistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancelByRescueStation(String phoneNumber) throws Exception;// sos, ok
    ListHistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception; // sos
}
