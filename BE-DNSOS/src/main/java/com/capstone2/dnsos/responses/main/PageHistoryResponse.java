package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.History;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageHistoryResponse {

    private String status;
    private Long id;
    private String createdAt;
    private String updatedAt;
    private String rescueStationName;
    private String user;

    public static PageHistoryResponse mapper(History history) {
        return PageHistoryResponse.builder()
                .id(history.getId())
                .status(history.getStatus().toString())
                .createdAt(history.getCreatedAt().toString())
                .updatedAt(history.getUpdatedAt().toString())
                .rescueStationName(history.getRescueStation().getRescueStationsName())
                .user(String.format("%s %s", history.getUser().getLastName(), history.getUser().getLastName()))
                .build();
    }
}
