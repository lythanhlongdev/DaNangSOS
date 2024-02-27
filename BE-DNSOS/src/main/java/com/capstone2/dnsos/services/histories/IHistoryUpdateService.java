package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.responses.main.HistoryUserResponses;

import java.util.List;

public interface IHistoryUpdateService {
    boolean updateHistoryStatus(StatusDTO statusDTO) throws Exception; // sos' , ok

    boolean updateHistoryStatusConfirmed(ConfirmedDTO confirmedDTO) throws Exception; // sos ok

    boolean updateHistoryStatusCancelUser(CancelDTO cancelDTO) throws Exception;// user

    boolean updateHistoryStatusCancel(CancelDTO cancelDTO) throws Exception; // sos

    HistoryUserResponses changeRescueStation(Long historyId) throws Exception;

    History updateHistoryGPS(GpsDTO gpsDTO) throws Exception;// ok

}
