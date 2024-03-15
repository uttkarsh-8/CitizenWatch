package com.policeapi.policerestapis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.policeapi.policerestapis.dto.ReportDto;
import com.policeapi.policerestapis.model.User;
import com.policeapi.policerestapis.repository.UserRepository;
import com.policeapi.policerestapis.service.FileStorageService;
import com.policeapi.policerestapis.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final ObjectMapper objectMapper;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;


    public ReportController(ReportService reportService, ObjectMapper objectMapper, FileStorageService fileStorageService, UserRepository userRepository) {
        this.reportService = reportService;
        this.objectMapper = objectMapper;
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ReportDto> createReport(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @RequestParam("file") MultipartFile file,
            @RequestParam("report") String reportStr) throws IOException {

        // Deserialize JSON string to ReportDto
        ReportDto reportDto = objectMapper.readValue(reportStr, ReportDto.class);

        // Find the User entity by the username
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Set the User on the ReportDto
        reportDto.setUserId(user.getId());

        // Handle file upload and get the URL to access the file
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        // Set the file URL in the report DTO
        reportDto.setImageUrl(fileDownloadUri);

        // Create report using the service layer
        ReportDto createdReport = reportService.createReport(reportDto);

        // Build the URI of the created report
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdReport.getId())
                .toUri();

        // Return the response with status 201 Created
        return ResponseEntity.created(location).body(createdReport);
    }

    // Add an endpoint to serve the files
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = "application/octet-stream"; // You might want to determine the actual type

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
