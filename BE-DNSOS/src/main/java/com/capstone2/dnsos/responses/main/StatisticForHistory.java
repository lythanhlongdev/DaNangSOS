package com.capstone2.dnsos.responses.main;


import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticForHistory {

    private Long total;
    private Long otherStatus;
    private Long completed;
    private Long totalCancel;
    private Long userCancel;
    private Long Rescue_Cancel;

//    public  StatisticForHistory mapper(Object[] objects){
//        return StatisticForHistory.builder()
//                .total(Objec)
//                .build();
//    }
}
