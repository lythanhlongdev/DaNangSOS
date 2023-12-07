package com.capstone2.dnsos.listeners;

import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.services.IHistoryChangeLogService;
import com.capstone2.dnsos.services.impl.HistoryChangeLogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HistoryLogListener {

    private static final String INSERT_EVENT = "INSERT";
    private static final String UPDATE_EVENT = "UPDATE";
    private static final String DELETE_EVENT = "DELETE";


}
