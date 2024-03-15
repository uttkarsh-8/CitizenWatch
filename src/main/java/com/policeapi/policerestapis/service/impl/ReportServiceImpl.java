package com.policeapi.policerestapis.service.impl;

import com.policeapi.policerestapis.dto.ReportDto;
import com.policeapi.policerestapis.exception.ResourceNotFoundException;
import com.policeapi.policerestapis.model.Report;
import com.policeapi.policerestapis.model.User;
import com.policeapi.policerestapis.repository.ReportRepository;
import com.policeapi.policerestapis.repository.UserRepository;
import com.policeapi.policerestapis.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ModelMapper modelMapper;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(ModelMapper modelMapper, ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ReportDto createReport(ReportDto reportDto) {

        if (reportDto.getStatus() == null || reportDto.getStatus().isEmpty()) {
            reportDto.setStatus("IN_PROGRESS");
        }

        User user = userRepository.findById(reportDto.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + reportDto.getUserId()));

        Report report = modelMapper.map(reportDto, Report.class);
        report.setUser(user);

        Report savedReport = reportRepository.save(report);

        return modelMapper.map(savedReport, ReportDto.class);
    }


    @Override
    public List<ReportDto> getAllReports() {
        List<Report> reports = reportRepository.findAll();

        return reports.stream().map
                (report -> modelMapper.map(report, ReportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto getReportById(long id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));

        return modelMapper.map(report,ReportDto.class);
    }

    @Override
    public ReportDto updateReport(ReportDto reportDto, long id) {



        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));

        report.setTitle(reportDto.getTitle());
        report.setDescription(reportDto.getDescription());
        report.setLocation(reportDto.getLocation());
        report.setType(reportDto.getType());
        report.setImageUrl(reportDto.getImageUrl());

        report.setStatus(reportDto.getStatus());

        Report updatedReport = reportRepository.save(report);

        return modelMapper.map(updatedReport, ReportDto.class);
    }



    @Override
    public void deleteReport(long id) {
        reportRepository.deleteById(id);

    }

    @Override
    public ReportDto updateReportStatus(long id, String status) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));
        report.setStatus(status);

        Report updatedReport = reportRepository.save(report);

        return modelMapper.map(updatedReport, ReportDto.class);
    }
}
