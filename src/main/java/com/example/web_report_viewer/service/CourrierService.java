package com.example.web_report_viewer.service;

import com.example.web_report_viewer.entities.Courier;
import com.example.web_report_viewer.entities.TransmissionSheet;
import com.example.web_report_viewer.repository.CourrierRepository;
import com.example.web_report_viewer.repository.TransmissionSheetRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

@Service
public class CourrierService {

    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private TransmissionSheetRepository transmissionSheetRepository;

    public byte[] exportReport(String reportFormat, Long sheetId) throws FileNotFoundException, JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Optional<TransmissionSheet> optionalTransmissionSheet = transmissionSheetRepository.findById(sheetId);

        if (!optionalTransmissionSheet.isPresent()) {
            throw new FileNotFoundException("Aucun bordereau n'a été trouvé avec cet ID: " + sheetId);
        }

        TransmissionSheet sheet = optionalTransmissionSheet.get();
        List<Courier> couriers = courrierRepository.findByTransmissionSheet(sheet);


        File file = ResourceUtils.getFile("classpath:courrier.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());


        JRBeanCollectionDataSource couriersDataSource = new JRBeanCollectionDataSource(couriers);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("num_bordereau", sheet.getNumBordereau());
        parameters.put("provenance", sheet.getProvenance());
        parameters.put("destination", sheet.getDestination());
        parameters.put("date_emission", sheet.getDateEmission());
        parameters.put("nbre_courrier", couriers.size());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, couriersDataSource);

        if (jasperPrint != null) {
            System.out.println("Le rapport a été généré avec succès.");// Enregistrez ou affichez le rapport
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C://Users//Vadil Kwekam/rapport5.pdf");
        } else {
            System.out.println("Erreur : Le rapport n'a pas été généré.");
        }

        switch (reportFormat.toLowerCase()) {
            case "pdf":
                return JasperExportManager.exportReportToPdf(jasperPrint);
            case "excel":
                JRXlsExporter xlsExporter = new JRXlsExporter();
                xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                SimpleXlsxReportConfiguration xlsxConfiguration = new SimpleXlsxReportConfiguration();
                xlsxConfiguration.setDetectCellType(true);
                xlsxConfiguration.setOnePagePerSheet(false);
                xlsxConfiguration.setRemoveEmptySpaceBetweenRows(true);
                xlsExporter.setConfiguration(xlsxConfiguration);
                xlsExporter.exportReport();
                return outputStream.toByteArray();
            case "word":
                JRDocxExporter docxExporter = new JRDocxExporter();
                docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                SimpleDocxReportConfiguration docxConfiguration = new SimpleDocxReportConfiguration();
                docxConfiguration.setFlexibleRowHeight(true);
                docxExporter.setConfiguration(docxConfiguration);
                docxExporter.exportReport();
                return outputStream.toByteArray();
            default:
                throw new IllegalArgumentException("Format de rapport non supporté: " + reportFormat);
        }
    }
}
