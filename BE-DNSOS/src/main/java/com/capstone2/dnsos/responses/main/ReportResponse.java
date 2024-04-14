package com.capstone2.dnsos.responses.main;
import com.capstone2.dnsos.models.main.Report;
import lombok.*;


@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private String createdAt;

    private  String description;

    private String role;

    public  static ReportResponse ReportMapperResponse(Report report){
        return  ReportResponse.builder()
                .createdAt(report.getCreatedAt().toString())
                .description(report.getDescription())
                .role(report.getRole())
                .build();
    }
}
