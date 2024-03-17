package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryLog;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.repositories.main.IHistoryLogRepository;
import com.capstone2.dnsos.responses.main.HistoryLogResponses;
import com.capstone2.dnsos.services.histories.IHistoryChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class HistoryChangeLogServiceImpl implements IHistoryChangeLogService {

    private final IHistoryLogRepository historyLogRepository;
    private final String USER = "USER";
    private final String RESCUE_STATION = "RESCUE_STATION";

    @Override
    public void saveLogEntry(List<HistoryLog> logEntries) {
        historyLogRepository.saveAll(logEntries);
    }

    @Override
    public void createLog(History newHistory, String eventType) throws Exception {
        List<HistoryLog> logs;
        logs = List.of(
                new HistoryLog("latitude", " ", newHistory.getLatitude().toString(), eventType, USER, newHistory),
                new HistoryLog("longitude", " ", newHistory.getLongitude().toString(), eventType, USER, newHistory),
                new HistoryLog("note", " ", newHistory.getNote(), eventType, USER, newHistory),
                new HistoryLog("Status", " ", newHistory.getStatus().toString(), eventType, USER, newHistory));
        saveLogEntry(logs);
    }

    @Override
    public void updateLog(History oldHistory, History newHistory, String eventType) throws Exception {
        // newHistory = existingHistory because it get in leve cache 1, not have event change => bug
        List<HistoryLog> logs = new ArrayList<>();
        if (!newHistory.getLongitude().equals(oldHistory.getLongitude())) {
            logs.add(new HistoryLog("latitude",
                    oldHistory.getLongitude().toString()
                    , newHistory.getLongitude().toString()
                    , eventType
                    , USER
                    , newHistory));
        }
        if (!newHistory.getLatitude().equals(oldHistory.getLatitude())) {
            logs.add(new HistoryLog("longitude"
                    , oldHistory.getLatitude().toString()
                    , newHistory.getLatitude().toString()
                    , eventType
                    , USER
                    , newHistory));
        }
        if (!newHistory.getNote().equals(oldHistory.getNote())) {
            logs.add(new HistoryLog("note"
                    , oldHistory.getNote()
                    , newHistory.getNote()
                    , eventType
                    , USER
                    , newHistory));
        }
        if (newHistory.getStatus().getValue() > oldHistory.getStatus().getValue()) {
            int status = newHistory.getStatus().getValue();
            if (status <= 3) {
                logs.add(new HistoryLog("Status"
                        , oldHistory.getStatus().toString()
                        , newHistory.getStatus().toString()
                        , eventType
                        , RESCUE_STATION
                        , newHistory));
            } else {
                if (status == 4) {
                    logs.add(new HistoryLog("Status"
                            , oldHistory.getStatus().toString()
                            , newHistory.getStatus().toString()
                            , eventType
                            , RESCUE_STATION
                            , newHistory));
                } else if (status == 5) {
                    logs.add(new HistoryLog("Status"
                            , oldHistory.getStatus().toString()
                            , newHistory.getStatus().toString()
                            , eventType
                            , USER
                            , newHistory));
                }
            }
        }

        if (!logs.isEmpty()) {
            saveLogEntry(logs);
        }
    }

    @Override
    public void updateMediaLog(HistoryMedia oldHistoryMedia, HistoryMedia newHistoryMedia, String eventType) throws Exception {
        List<HistoryLog> logs = new ArrayList<>();
        if (newHistoryMedia != null) {
            if (newHistoryMedia.getImage1() != null) {
                logs.add(new HistoryLog("img1", oldHistoryMedia.getImage1(), newHistoryMedia.getImage1(), eventType, USER, newHistoryMedia.getHistory()));
            }
            if (newHistoryMedia.getImage2() != null) {
                logs.add(new HistoryLog("img2", oldHistoryMedia.getImage2(), newHistoryMedia.getImage2(), eventType, USER, newHistoryMedia.getHistory()));
            }
            if (newHistoryMedia.getImage3() != null) {
                logs.add(new HistoryLog("img3", oldHistoryMedia.getImage3(), newHistoryMedia.getImage3(), eventType, USER, newHistoryMedia.getHistory()));
            }
            if (newHistoryMedia.getVoice() != null) {
                logs.add(new HistoryLog("voice", oldHistoryMedia.getVoice(), newHistoryMedia.getVoice(), eventType, USER, newHistoryMedia.getHistory()));
            }
        }
        if (!logs.isEmpty()) {
            saveLogEntry(logs);
        }
    }

    @Override
    public void deleteLog(History newHistory, String eventType) throws Exception {

    }

    private boolean isEqual(Objects obj1, Objects obj2) {
        return Objects.equals(obj1, obj2);
    }

    @Override
    public List<HistoryLogResponses> readLogByHistoryId(Long historyId) throws Exception {
        if (!historyLogRepository.existsHistoryLogByHistory_Id(historyId)){
            throw  new NotFoundException("history does not exist with id: "+historyId);
        }
        List<HistoryLog>  historyLogs = historyLogRepository.findAllByHistory_IdOrderByEventTimeAsc(historyId);
        if(!historyLogs.isEmpty()){
            return  historyLogs.stream().map(HistoryLogResponses::mapperEntity).toList();
        }
        return null;
    }

}
