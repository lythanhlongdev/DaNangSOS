package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {


    @JsonProperty("history_id")
    @NotNull(message = "history id is requirement ")
    private Long historyId;

    @JsonProperty("status")
    @NotNull(message = "status is requirement ")
    @Min(value = 1, message = "Input status 1 -> 4")
    @Max(value = 4, message = "Input status 1 -> 4")
    private int status;
}
