package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmedDTO {

    @JsonProperty("phone_number")
    @NotNull(message = "phone number is requirement ")
    private String phoneNumber;
    @JsonProperty("history_id")
    @NotNull(message = "phone number is requirement ")
    private Long historyId;

}
