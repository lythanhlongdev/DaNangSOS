package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public class HistoryMediaDTO {


    @JsonProperty("history_id")
    private  Long historyId;
    @Size(min = 5, max = 128, message = "Image's name ")
    private String image1;
    @Size(min = 5, max = 128, message = "Image's name ")
    private String image2;
    @Size(min = 5, max = 128, message = "Image's name ")
    private String image3;
    @Size(min = 5, max = 128, message = "voice name ")
    private String voice;
}
