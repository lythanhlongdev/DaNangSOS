package com.capstone2.dnsos.services.reports;

import com.capstone2.dnsos.dto.ReportDTO;
import com.capstone2.dnsos.models.main.Report;

import java.util.List;

public interface IReportService {
    Report createReport(ReportDTO reportDto) throws Exception;
    List<Report> readReports(Long history_id) throws Exception;
}
