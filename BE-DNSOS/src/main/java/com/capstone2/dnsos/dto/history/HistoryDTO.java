package com.capstone2.dnsos.dto.history;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    @JsonProperty("user_id")
    @NotNull(message = "User id is requirement")
    private Long userId;
    @NotNull(message = "latitude id is requirement")
    private Double latitude;// vi do
    @NotNull(message = "longitude id is requirement")
    private Double longitude;// kinh do
    @Size( max =  200, message = "Note: Enter a maximum of 200 characters")
    private String note;

}
