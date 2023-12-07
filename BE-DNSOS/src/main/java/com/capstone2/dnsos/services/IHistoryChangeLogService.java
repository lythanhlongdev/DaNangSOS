package com.capstone2.dnsos.services;

import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryLog;
import com.capstone2.dnsos.models.HistoryMedia;

import java.util.List;

public interface IHistoryChangeLogService {

    void createLog(History newHistory, String eventType) throws Exception;

    void updateLog(History oldHistory, History newHistory,String eventType) throws Exception;

    void updateMedia(HistoryMedia oldHistoryMedia, HistoryMedia newHistoryMedia,String eventType) throws Exception;
    void deleteLog(History newHistory, String eventType) throws Exception;

    void saveLogEntry(List<HistoryLog> logEntries) throws Exception;
}
