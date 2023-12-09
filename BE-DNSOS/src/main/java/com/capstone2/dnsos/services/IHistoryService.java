package com.capstone2.dnsos.services;

import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
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

    History getHistoryById(@NotNull Long historyId) throws Exception; //  oke

    List<ListHistoryByUserResponses> getAllHistoryByUser(String phoneNumber) throws Exception;// user, ok

    List<ListHistoryByRescueStationResponses> getAllHistoryByRescueStation(String phoneNumber) throws Exception;// sos, ok

    List<ListHistoryByRescueStationResponses> getAllHistoryNotConfirmedAndCancelByRescueStation(String phoneNumber) throws Exception;// sos, ok
    ListHistoryByRescueStationResponses getHistoryByIdForApp(ConfirmedDTO confirmedDTO) throws Exception; // sos

    boolean updateHistoryStatus(StatusDTO statusDTO) throws Exception; // sos' , ok

    boolean updateHistoryStatusConfirmed(ConfirmedDTO confirmedDTO) throws Exception; // sos ok

    boolean updateHistoryStatusCancelUser(CancelDTO cancelDTO) throws Exception;// user

    boolean updateHistoryStatusCancel(CancelDTO cancelDTO) throws Exception; // sos

    History updateHistoryGPS(GpsDTO gpsDTO) throws Exception;// ok
}

