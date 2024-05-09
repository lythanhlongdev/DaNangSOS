package com.capstone2.dnsos.dto.history;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    @NotNull(message = "Cần phải nhập vĩ độ")
    private Double latitude;// vi do
    @NotNull(message = "Cần phải nhập kinh độ")
    private Double longitude;// kinh do

}
