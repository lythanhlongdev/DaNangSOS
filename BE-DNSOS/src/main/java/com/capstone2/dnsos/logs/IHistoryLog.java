package com.capstone2.dnsos.logs;

import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;

public interface IHistoryLog {
    void afCreate(History history) throws Exception;
     void onUpdate(History oldHistory, History newHistory) throws Exception;
     void onUpdateMedia(HistoryMedia oldHistoryMedia, HistoryMedia newHistoryMedia) throws Exception;
}
