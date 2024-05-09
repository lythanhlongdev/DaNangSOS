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
    @NotNull(message = "Cần phải nhập history id")
    private Long historyId;

    @JsonProperty("status")
    @NotNull(message = "Cần phải nhập trạng thái")
    @Min(value = 1, message = "Giá trị trong khoảng 1-4")
    @Max(value = 4, message = "Giá trị trong khoảng 1-4")
    private int status;
}
