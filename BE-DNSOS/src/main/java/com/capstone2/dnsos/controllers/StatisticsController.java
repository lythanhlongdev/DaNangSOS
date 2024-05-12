package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.repositories.main.IHistoryRescueRepository;
import com.capstone2.dnsos.repositories.main.IRescueStationRepository;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.Statistics.IStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/statistics")
public class StatisticsController {
    private final IStatisticsService statisticsService;
    private final Set<String> DATE_SET = new HashSet<>(Arrays.asList("date", "month", "year"));

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/histories")
    public ResponseEntity<?> statisticsAllHistory(@RequestParam(defaultValue = "0") String field, @RequestParam(defaultValue = "0") String dateTime) {
        try {
            String trimmedField = field.trim();
            if (!DATE_SET.contains(trimmedField)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponsesEntity("Tham số 'field' chỉ nhận dữ liệu {date,month,year}", HttpStatus.BAD_REQUEST.value(), ""));
            }

            HistorySummaryResponse statisticsForHistories = statisticsService.StatisticsForHistories(field, dateTime);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Lấy dữ liệu thống kê tin hiệu cầu cứu thành công", HttpStatus.OK.value(), statisticsForHistories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/recue_stations")
    public ResponseEntity<?> statisticsAllRescueStation(@RequestParam(defaultValue = "0") String field, @RequestParam(defaultValue = "0") @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTime) {
        try {
            String trimmedField = field.trim();
            if (!DATE_SET.contains(trimmedField)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponsesEntity("Tham số 'field' chỉ nhận dữ liệu {date,month,year}", HttpStatus.BAD_REQUEST.value(), ""));
            }
            List<StatisticsRescueStationHaveSos> statisticsForHistories = statisticsService.StatisticsForRescueStation(field, dateTime);
            int elements = statisticsForHistories.size();
            PageStatisticsAllRescueStation pageStatisticsAllRescueStation = PageStatisticsAllRescueStation.builder()
                    .statisticsRescueStationHaveSos(statisticsForHistories)
                    .elements(elements)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Lấy dữ liệu thống kê tin hiệu cầu cứu thành công", HttpStatus.OK.value(), pageStatisticsAllRescueStation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> statisticsAllRescueStation() {
        try {
            StatisticsUserActivityAndRecueStation statisticsUserActivityAndRecueStation = statisticsService.STATISTICS_USER_ACTIVITY_AND_RECUE_STATION();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Lấy dữ liệu thống kê trạm thành công", HttpStatus.OK.value(), statisticsUserActivityAndRecueStation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }

}
