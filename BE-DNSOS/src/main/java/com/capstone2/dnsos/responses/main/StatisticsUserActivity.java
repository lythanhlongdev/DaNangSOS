package com.capstone2.dnsos.responses.main;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsUserActivity {
    private Long total;
    private Long unlock;
    private Long lock;
}
