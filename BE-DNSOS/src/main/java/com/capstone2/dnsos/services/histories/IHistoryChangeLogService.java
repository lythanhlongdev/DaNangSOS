package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryLog;
import com.capstone2.dnsos.models.main.HistoryMedia;

import java.util.List;

public interface IHistoryChangeLogService {

    void createLog(History newHistory, String eventType) throws Exception;

    void updateLog(History oldHistory, History newHistory,String eventType) throws Exception;

    void updateMediaLog(HistoryMedia oldHistoryMedia, HistoryMedia newHistoryMedia, String eventType) throws Exception;
    void deleteLog(History newHistory, String eventType) throws Exception;

    void saveLogEntry(List<HistoryLog> logEntries) throws Exception;
}
