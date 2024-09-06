package com.example.web_report_viewer.repository;

import com.example.web_report_viewer.entities.Courier;
import com.example.web_report_viewer.entities.TransmissionSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourrierRepository extends JpaRepository<Courier, Long> {
    List<Courier> findByTransmissionSheet(TransmissionSheet transmissionSheet);
}
