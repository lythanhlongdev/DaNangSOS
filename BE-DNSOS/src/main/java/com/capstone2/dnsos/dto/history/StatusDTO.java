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


    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(0|\\+84)(86|96|97|98|32|33|34|91|94|88|90|93|92)\\d{7}$", message = "Invalid phone number format")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("history_id")
    @NotNull(message = "history id is requirement ")
    private Long historyId;

    @JsonProperty("status")
    @NotNull(message = "status is requirement ")
    @Min(value = 1, message = "Input status 1 -> 3")
    @Max(value = 3, message = "Input status 1 -> 3")
    private int status;
}
