package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CancelDTO {

    @JsonProperty("phone_number")
    @NotNull(message = "phone number is requirement ")
    private String phoneNumber;
    @JsonProperty("history_id")
    @NotNull(message = "phone number is requirement ")
    private Long historyId;
    @NotNull(message = "Note is requirement")
    private String note;
}
