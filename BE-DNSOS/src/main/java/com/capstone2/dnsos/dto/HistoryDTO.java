package com.capstone2.dnsos.dto;


import com.capstone2.dnsos.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@Builder
public class HistoryDTO {

    @NotNull(message = "")
    private Point GPS;
    private byte[] voice;
    private String note;
    private Status status;

    private byte[] image1;
    private byte[] image2;
    private byte[] image3;

}
