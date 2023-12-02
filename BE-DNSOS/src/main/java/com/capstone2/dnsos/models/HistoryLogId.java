package com.capstone2.dnsos.models;

import com.capstone2.dnsos.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class HistoryLogId implements Serializable {
    private Long logId;
    private Status status;
}
