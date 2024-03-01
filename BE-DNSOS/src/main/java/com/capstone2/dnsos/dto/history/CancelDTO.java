package com.capstone2.dnsos.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CancelDTO {


    @Min(value = 0, message = "Invalid history id")
    @NotNull(message = "history_id is requirement ")
    @JsonProperty("history_id")
    private Long historyId;

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("user_phone_number")
    private String userPhoneNumber;

    private String note;
}
