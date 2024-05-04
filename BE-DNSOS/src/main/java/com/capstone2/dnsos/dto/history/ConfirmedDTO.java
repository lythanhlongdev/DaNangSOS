package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmedDTO {


    @JsonProperty("history_id")
    @NotNull(message = "Cần phải nhập history id")
    private Long historyId;

    @NotBlank(message = "Cần phải nhập số điện thoại")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Nhập số điện thoại sai định dạng")
    @JsonProperty("rescue_phone_number")
    private String rescuePhoneNumber;

}
