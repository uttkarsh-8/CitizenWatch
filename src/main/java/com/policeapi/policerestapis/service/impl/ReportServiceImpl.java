package com.policeapi.policerestapis.service.impl;

import com.policeapi.policerestapis.dto.ReportDto;
import com.policeapi.policerestapis.exception.ResourceNotFoundException;
import com.policeapi.policerestapis.model.Report;
import com.policeapi.policerestapis.model.User;
import com.policeapi.policerestapis.repository.ReportRepository;
import com.policeapi.policerestapis.repository.UserRepository;
import com.policeapi.policerestapis.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Fetch the User entity using the userId from the ReportDto
        User user = userRepository.findById(reportDto.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + reportDto.getUserId()));

        // DTO to entity
        Report report = modelMapper.map(reportDto, Report.class);

        // Set the User entity to the report
        report.setUser(user);

        // Save the Report entity
        Report savedReport = reportRepository.save(report);

        // Entity to DTO
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
        Report report = reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + id));

        report.setType(reportDto.getType());
        report.setTitle(reportDto.getTitle());
        report.setLocation(reportDto.getLocation());
        report.setDescription(reportDto.getDescription());
        report.setImageUrl(reportDto.getImageUrl());

        reportRepository.save(report);

        return modelMapper.map(report, ReportDto.class);
    }

    @Override
    public void deleteReport(long id) {
        reportRepository.deleteById(id);

    }
}
