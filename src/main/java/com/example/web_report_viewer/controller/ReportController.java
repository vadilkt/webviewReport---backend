package com.example.web_report_viewer.controller;

import com.example.web_report_viewer.service.CourrierService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private CourrierService courrierService;

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport(@RequestParam String format, @RequestParam Long sheetId){
        try {
            if(format == null || format.isEmpty() || sheetId==null){
                return ResponseEntity.badRequest().body("Format ou Id de bordereau non spécifié".getBytes());
            }
            System.out.println("Je marche");
            byte[] report = courrierService.exportReport(format, sheetId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report."+format)
                    .contentType(getMediaTypeForFormat(format))
                    .body(report);
        } catch (JRException e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la génération du rapport".getBytes());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne du serveur".getBytes());
        }
    }

    private MediaType getMediaTypeForFormat(String format){
        switch (format.toLowerCase()){
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "excel":
                return MediaType.parseMediaType("application/vnd.ms-excel");
            case "word":
                return MediaType.parseMediaType("application/msword");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;

        }
    }
}
