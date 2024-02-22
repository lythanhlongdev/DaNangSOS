package com.capstone2.dnsos.common;


import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KilometerMin {
    private  Long rescueStationID;
    private String rescueStationName;
    private Double kilometers;
    private  int count;
}
