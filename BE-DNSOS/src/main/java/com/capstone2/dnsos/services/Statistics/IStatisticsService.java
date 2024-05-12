package com.capstone2.dnsos.services.Statistics;

import com.capstone2.dnsos.responses.main.HistorySummaryResponse;
import com.capstone2.dnsos.responses.main.StatisticsRescueStationHaveSos;
import com.capstone2.dnsos.responses.main.StatisticsUserActivityAndRecueStation;
import com.capstone2.dnsos.responses.main.StatusRescueStationResponse;

import java.util.List;

public interface IStatisticsService {

    StatusRescueStationResponse totalStatusRescueStation() throws  Exception;
    StatisticsUserActivityAndRecueStation STATISTICS_USER_ACTIVITY_AND_RECUE_STATION() throws  Exception;
    HistorySummaryResponse StatisticsForHistories(String field, String dateTime) throws Exception;
    List<StatisticsRescueStationHaveSos> StatisticsForRescueStation(String field, String dateTime) throws Exception;
}
