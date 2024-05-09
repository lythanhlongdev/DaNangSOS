package com.capstone2.dnsos.responses.main;

import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PageRescueResponses {
    List<PageRescueResponse> listRescueResponses;
    int totalPages;
    long totalElements;
}
