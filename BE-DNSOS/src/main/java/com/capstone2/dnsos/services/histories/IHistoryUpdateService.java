package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.ConfirmedDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;
import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.responses.main.HistoryByGPSResponse;
import com.capstone2.dnsos.responses.main.HistoryResponse;
import com.capstone2.dnsos.responses.main.HistoryUserResponses;

public interface IHistoryUpdateService {
    Status updateHistoryStatus(StatusDTO statusDTO) throws Exception; // sos' , ok

    boolean updateHistoryStatusConfirmed(ConfirmedDTO confirmedDTO) throws Exception; // sos ok

    void updateHistoryStatusCancelUser(CancelDTO cancelDTO) throws Exception;// user

    HistoryResponse updateHistoryStatusCancel(CancelDTO cancelDTO) throws Exception; // sos

    HistoryUserResponses changeRescueStation(Long historyId) throws Exception;

    HistoryByGPSResponse updateHistoryGPS(GpsDTO gpsDTO) throws Exception;// ok

}
