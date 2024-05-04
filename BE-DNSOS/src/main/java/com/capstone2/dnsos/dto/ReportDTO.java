package com.capstone2.dnsos.dto;


import com.capstone2.dnsos.models.main.History;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    @NotNull(message = "Cần phải nhập history id")
    @JsonProperty("history_id")
    private Long history;

    @NotNull(message = "Cần phải nhập history id")
    @Max(value = 2, message = "Giá trị hợp lệ trong khoảng (1 - 2)")
    @Min(value = 1, message = "Giá trị hợp lệ trong khoảng (1 - 2)")
    @JsonProperty("role")
    private byte role ;

    @NotBlank(message = "Cần phải nhập mô tả" )
    @Length(max = 255, message = "Mô tả chỉ có độ dài dưới 255 ký tự")
    @JsonProperty("description" )
    private String description;

}
