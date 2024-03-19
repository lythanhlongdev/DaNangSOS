package com.capstone2.dnsos.responses.main;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserPageResponses {
    private List<UserResponses> userNotPasswordResponses;
    private  int totalPage;
}
