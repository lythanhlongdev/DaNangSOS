package com.capstone2.dnsos.services.Statistics.impl;

import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.repositories.main.IUserRepository;
import com.capstone2.dnsos.responses.main.HistorySummaryResponse;
import com.capstone2.dnsos.responses.main.StatisticsRescueStationHaveSos;
import com.capstone2.dnsos.responses.main.StatisticsUserActivityAndRecueStation;
import com.capstone2.dnsos.responses.main.StatusRescueStationResponse;
import com.capstone2.dnsos.services.Statistics.IStatisticsService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticsService implements IStatisticsService {

    private final IHistoryRepository historyRescueRepository;
    private final IRescueStationRepository rescueStationRepository;
    private final IUserRepository userRepository;


    @Override
    public StatisticsUserActivityAndRecueStation STATISTICS_USER_ACTIVITY_AND_RECUE_STATION() throws Exception {
        return StatisticsUserActivityAndRecueStation.builder()
                .statisticsUserActivity(userRepository.getUserActivity())
                .rescueStationResponse(rescueStationRepository.totalStatusRescueStation())
                .build();
    }

    @Override
    public StatusRescueStationResponse totalStatusRescueStation() throws Exception {
        return rescueStationRepository.totalStatusRescueStation();
    }

    @Override
    public HistorySummaryResponse StatisticsForHistories(String field, String dateTime) throws Exception {
        if (dateTime == null || dateTime.isEmpty()) {
            throw new InvalidParamException("Tham số 'dateTime' không được để trống");
        }
        switch (field) {
            case "date": {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dateTime.trim(), formatter);
                return historyRescueRepository.getHistoryStatisticsByDay(localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear());
            }
            case "month": {
                if (dateTime.equals("0")) {
                    throw new InvalidParamException("Lấy thông kế theo tháng status = 2 nên là tham số dateTime đầy đủ tham số dd-mm-yyy và không được để trống");
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dateTime.trim(), formatter);
                return historyRescueRepository.getHistoryStatisticsByMonth(localDate.getMonthValue(), localDate.getYear());
            }
            case "year": {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dateTime.trim(), formatter);
                return historyRescueRepository.getHistoryStatisticsByYear(localDate.getYear());
            }
            default:
                return historyRescueRepository.getHistoryStatistics();
        }
    }


    @Override
    public List<StatisticsRescueStationHaveSos> StatisticsForRescueStation(String field, String dateTime) throws Exception {
        if (dateTime == null || dateTime.isEmpty()) {
            throw new InvalidParamException("Tham số 'dateTime' không được để trống");
        }
        switch (field) {
            case "date": {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dateTime.trim(), formatter);
                return rescueStationRepository.getRescueStationHaveSosByDate(localDate);
            }
            case "month": {
                if (dateTime.equals("0")) {
                    throw new InvalidParamException("Lấy thông kế theo tháng status = 2 nên là tham số dateTime đầy đủ tham số dd-mm-yyy và không được để trống");
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(dateTime.trim(), formatter);
                    return rescueStationRepository.getRescueStationHaveSosByMoth(localDate.getMonthValue(), localDate.getYear());
                }
            }
            case "year": {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dateTime.trim(), formatter);
                return rescueStationRepository.getRescueStationHaveSosByYear(localDate.getYear());
            }
            default:
                return rescueStationRepository.getRescueStationHaveSos();
        }
    }
}
