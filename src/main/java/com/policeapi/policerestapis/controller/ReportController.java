package com.policeapi.policerestapis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.policeapi.policerestapis.dto.ReportDto;
import com.policeapi.policerestapis.service.FileStorageService;
import com.policeapi.policerestapis.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final ObjectMapper objectMapper;
    @Autowired
    private final FileStorageService fileStorageService;


    public ReportController(ReportService reportService, ObjectMapper objectMapper, FileStorageService fileStorageService) {
        this.reportService = reportService;
        this.objectMapper = objectMapper;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResponseEntity<ReportDto> createReport(@RequestBody ReportDto reportDto){
        ReportDto report = reportService.createReport(reportDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(report.getId())
                .toUri();

        return ResponseEntity.created(location).body(report);
    }

    @GetMapping
    public ResponseEntity<List<ReportDto>> getAllReports(){
        List<ReportDto> allReports = reportService.getAllReports();

        return ResponseEntity.ok(allReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getReportById(@PathVariable long id){
        ReportDto reportById = reportService.getReportById(id);

        return ResponseEntity.ok(reportById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDto> updateReport(@PathVariable long id, @RequestBody ReportDto reportDto){
        ReportDto updatedReport = reportService.updateReport(reportDto, id);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedReport.getId())
                .toUri();

        return ResponseEntity.created(location).body(updatedReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);

        return ResponseEntity.ok().build();
    }
}
