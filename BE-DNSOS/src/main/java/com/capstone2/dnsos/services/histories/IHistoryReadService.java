package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.responses.main.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHistoryReadService {

    //    HistoryInMapAppResponse getCurrentHistoryInMapUser(@NotNull Long historyId) throws Exception; //  oke
    HistoryInMapAppResponse getCurrentHistoryInMapUser() throws Exception; //  oke

    HistoryInMapAppResponse getCurrentHistoryByIdInMapWorker(Long historyId) throws Exception;

    List<HistoryByUserResponses> getAllHistoryByUser() throws Exception;// user, ok

    List<HistoryByRescueStationResponses> getAllHistoryByRescueStation() throws Exception;// sos, ok

    List<HistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancel() throws Exception;// sos, ok

    HistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception; // sos

    Page<PageHistoryResponse> getAllHistoryForAdmin(Pageable pageable) throws Exception;

    DetailHistoryResponse getDetailHistoryById(Long historyId) throws Exception;
}
