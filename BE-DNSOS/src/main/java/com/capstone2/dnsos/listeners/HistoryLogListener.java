package com.capstone2.dnsos.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HistoryLogListener {

    private static final String INSERT_EVENT = "INSERT";
    private static final String UPDATE_EVENT = "UPDATE";
    private static final String DELETE_EVENT = "DELETE";


}
