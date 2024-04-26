package com.capstone2.dnsos.responses.main;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvatarResponse {
    private String avatarName;
    private Long userId;
}
