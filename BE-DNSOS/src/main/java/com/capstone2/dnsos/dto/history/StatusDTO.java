package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {


    @JsonProperty("phone_number")
    @NotNull(message = "phone number is requirement ")
    private String phoneNumber;
    @JsonProperty("history_id")
    @NotNull(message = "history id is requirement ")
    private Long historyId;

    @JsonProperty("status")
    @NotNull(message = "status is requirement ")

    @Min(value = 1, message = "Input status 1 -> 3")
    @Max(value = 3, message = "Input status 1 -> 3")
    private int status;
}
