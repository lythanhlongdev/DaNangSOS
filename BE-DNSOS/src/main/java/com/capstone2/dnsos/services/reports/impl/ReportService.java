package com.capstone2.dnsos.services.reports.impl;

import com.capstone2.dnsos.dto.ReportDTO;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.Report;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.repositories.main.IReportRepository;
import com.capstone2.dnsos.responses.main.ReportResponse;
import com.capstone2.dnsos.services.reports.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService implements IReportService {

    private final IReportRepository reportRepository;
    private final IHistoryRepository historyRepository;
    private final String[] ROLE = {"ADMIN", "RESCUE", "USER"};

    @Override
    public ReportResponse createReport(ReportDTO reportDto) throws Exception {
        History exstingHistory = this.getHistory(reportDto.getHistory());
        Report newReport = Report.builder()
                .history(exstingHistory)
                .role(reportDto.getRole() == 1 ? ROLE[1] : ROLE[2])
                .description(reportDto.getDescription())
                .build();
        return ReportResponse.ReportMapperResponse(reportRepository.save(newReport));
    }

    @Override
    public List<ReportResponse> readReports(Long historyId) throws Exception {
        if (!reportRepository.existsAllByHistory_Id(historyId)) {
            throw new NotFoundException("There are no reports for history with id: " + historyId);
        }
        return reportRepository.findAllByHistory_Id(historyId).stream().map(ReportResponse::ReportMapperResponse).toList();
    }

    public History getHistory(Long historyId) throws NotFoundException {
        return historyRepository.findById(historyId).orElseThrow(() -> new NotFoundException("Cannot find history with id: " + historyId));
    }
}
