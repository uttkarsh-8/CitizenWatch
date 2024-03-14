package com.policeapi.policerestapis.repository;

import com.policeapi.policerestapis.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
