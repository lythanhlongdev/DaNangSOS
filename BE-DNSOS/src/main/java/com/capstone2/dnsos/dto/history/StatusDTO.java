package com.capstone2.dnsos.dto.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {

    private  Long historyId;
    private  int status;
}
