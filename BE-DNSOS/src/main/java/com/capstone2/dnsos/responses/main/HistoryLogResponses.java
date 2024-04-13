package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.HistoryLog;
import lombok.*;


import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryLogResponses {

    private String eventType;
    private LocalDateTime time;
    private String role;
    private String fieldName;
    private Object eventValue;

    public static HistoryLogResponses mapperEntity(HistoryLog historyLog) {
        return HistoryLogResponses.builder()
                .eventType(historyLog.getEventType())
                .time(historyLog.getEventTime())
                .role(historyLog.getRole())
                .fieldName(historyLog.getFieldName())
                .eventValue(historyLog.getNewValue())
                .build();
    }

}
