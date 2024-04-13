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

    @NotNull(message = "history id is requirement !")
    @JsonProperty("history_id")
    private Long history;

    @NotNull(message = "history id is requirement !")
    @Max(value = 2, message = "role input two values [1,2] 1 is RESCUE, 2 is USER")
    @Min(value = 1, message = "role input two values [1,2] 1 is RESCUE, 2 is USER")
    @JsonProperty("role")
    private byte role ;

    @NotBlank(message = "description  name is requirement" )
    @Length(max = 255, message = " description is max 500 characters")
    @JsonProperty("description" )
    private String description;

}
