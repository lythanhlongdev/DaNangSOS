package com.capstone2.dnsos.listeners;


import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.services.IHistoryChangeLogService;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class HistoryChangeListener implements Serializable {

    @Autowired
    private ApplicationContext applicationContext;

    private static final String INSERT_EVENT = "INSERT";
    private static final String UPDATE_EVENT = "UPDATE";
    private static final String DELETE_EVENT = "DELETE";

    public HistoryChangeListener() {

    }

    @PostPersist
    public void afCreate(History history) throws Exception {
        handleEvents(history, INSERT_EVENT);
    }

    //    @PreUpdate
    public void onUpdate(History oldHistory, History newHistory) throws Exception {
        IHistoryChangeLogService changeLogService = applicationContext.getBean(IHistoryChangeLogService.class);
        changeLogService.updateLog(oldHistory,newHistory, UPDATE_EVENT);
    }

    @PostRemove
    public void afRemove(History history) throws Exception {
        handleEvents(history, UPDATE_EVENT);
    }


    private void handleEvents(History newHistory, String eventType) throws Exception {
        IHistoryChangeLogService changeLogService = applicationContext.getBean(IHistoryChangeLogService.class);
        if (INSERT_EVENT.equals(eventType)) {
            changeLogService.createLog(newHistory, eventType);
        } else if (UPDATE_EVENT.equals(eventType)) {
            // history in leve cache 1
//            changeLogService.updateLog(newHistory, eventType);
        } else if (DELETE_EVENT.equals(eventType)) {
            System.out.print("not have delete history");
        } else {
            throw new Exception("Error: Invalid event type in HistoryChangeListener");
        }
    }
}