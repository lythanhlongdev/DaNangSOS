package com.capstone2.dnsos.logs;


import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;
import com.capstone2.dnsos.services.IHistoryChangeLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryLog implements IHistoryLog {

    private final ApplicationContext applicationContext;
    private static final String INSERT_EVENT = "INSERT";
    private static final String UPDATE_EVENT = "UPDATE";
    private static final String DELETE_EVENT = "DELETE";

//    public HistoryLog(IHistoryChangeLogService changeLogService) {// use key this
//    }


    public void afCreate(History history) throws Exception {
        IHistoryChangeLogService changeLogService = applicationContext.getBean(IHistoryChangeLogService.class);
        changeLogService.createLog(history, INSERT_EVENT);
    }

    //    @PreUpdate
    public void onUpdate(History oldHistory, History newHistory) throws Exception {
        IHistoryChangeLogService changeLogService = applicationContext.getBean(IHistoryChangeLogService.class);
        changeLogService.updateLog(oldHistory, newHistory, UPDATE_EVENT);
    }

    @Override
    public void onUpdateMedia(HistoryMedia oldHistoryMedia, HistoryMedia newHistoryMedia) throws Exception {
        IHistoryChangeLogService changeLogService = applicationContext.getBean(IHistoryChangeLogService.class);
        changeLogService.updateMedia(oldHistoryMedia,newHistoryMedia,UPDATE_EVENT);
    }

    //    public void afRemove(History history,IHistoryChangeLogService changeLogService) throws Exception {
//        changeLogService.deleteLog(history,DELETE_EVENT);
//    }
//

}
