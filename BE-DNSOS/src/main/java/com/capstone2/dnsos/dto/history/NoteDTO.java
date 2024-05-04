package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    
    @JsonProperty("history_id")
    @NotNull(message = "history Id is requirement ")
    private Long historyId;
    @JsonProperty("note")
    private String note;
}
