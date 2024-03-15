package com.policeapi.policerestapis.service;

import com.policeapi.policerestapis.dto.ReportDto;

import java.util.List;

public interface ReportService {
    ReportDto createReport(ReportDto reportDto);
    List<ReportDto> getAllReports();
    ReportDto getReportById(long id);
    ReportDto updateReport(ReportDto reportDto, long id);
    void deleteReport(long id);
    ReportDto updateReportStatus(long id, String status);
}
