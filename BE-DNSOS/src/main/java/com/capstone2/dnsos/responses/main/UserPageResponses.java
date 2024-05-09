package com.capstone2.dnsos.responses.main;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserPageResponses {
    private List<PageUserResponse> userNotPasswordResponses;
    private  int totalPage;
    long totalElements;
}
