package com.capstone2.dnsos.services.reports;

import com.capstone2.dnsos.dto.ReportDTO;
import com.capstone2.dnsos.models.main.Report;
import com.capstone2.dnsos.responses.main.ReportResponse;

import java.util.List;

public interface IReportService {
    ReportResponse createReport(ReportDTO reportDto) throws Exception;
    List<ReportResponse> readReports(Long history_id) throws Exception;
}
