package com.example.web_report_viewer.repository;

import com.example.web_report_viewer.entities.TransmissionSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionSheetRepository extends JpaRepository<TransmissionSheet, Long> {
}
