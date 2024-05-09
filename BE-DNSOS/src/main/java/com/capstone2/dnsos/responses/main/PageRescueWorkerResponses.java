package com.capstone2.dnsos.responses.main;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PageRescueWorkerResponses {
    List<PageRescueWorkerResponse> listRescueWorker;
    int totalPages;
    long totalElements;
}
