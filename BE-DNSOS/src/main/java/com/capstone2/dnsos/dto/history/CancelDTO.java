package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelDTO {

    @Min(value = 0, message = "Nhập history id không hợp lệ")
    @NotNull(message = "Cần phải nhập history id")
    @JsonProperty("history_id")
    private Long historyId;

    private String note;
}
