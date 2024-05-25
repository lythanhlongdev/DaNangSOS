package com.capstone2.dnsos.responses.main;

import jakarta.persistence.Column;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistorySummaryResponse {
    private Long total;
    private Long totalOtherStatus;
    private Long totalCompleted;
    private Long totalCancel;
    private Long totalUserCancel;
    private Long totalRescueCancel;
}
