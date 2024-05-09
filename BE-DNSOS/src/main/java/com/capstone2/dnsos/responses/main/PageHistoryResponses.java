package com.capstone2.dnsos.responses.main;

import lombok.*;

import java.util.List;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageHistoryResponses {
    List<PageHistoryResponse> listRescueWorker;
    int totalPages;
    long totalElements;
}
