package com.example.web_report_viewer.service;

import com.example.web_report_viewer.entities.Courier;
import com.example.web_report_viewer.entities.TransmissionSheet;
import com.example.web_report_viewer.repository.CourrierRepository;
import com.example.web_report_viewer.repository.TransmissionSheetRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JasperReportSource;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourrierService {

    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private TransmissionSheetRepository transmissionSheetRepository;

    public byte[] exportReport(String reportFormat, Long sheetId) throws IOException, JRException {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome +"/Desktop/";
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName;
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

//        if (jasperPrint != null) {
//            System.out.println("Le rapport a été généré avec succès.");// Enregistrez ou affichez le rapport
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "C://Users//Vadil Kwekam/rapport9.pdf");
//        } else {
//            System.out.println("Erreur : Le rapport n'a pas été généré.");
//        }

        switch (reportFormat.toLowerCase()) {
            case "pdf":
                fileName = "bordereau_" + timestamp + ".pdf";
                String pdfPath = desktopPath + fileName;
                JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
                System.out.println("Fichier exporté à : " + pdfPath);
                return JasperExportManager.exportReportToPdf(jasperPrint);

            case "html":
                fileName = "bordereau_" + timestamp + ".html";
                String htmlPath = desktopPath + fileName;
                JasperExportManager.exportReportToHtmlFile(jasperPrint, htmlPath);
                System.out.println("Fichier exporté à : " + htmlPath);
                break;

            case "xml":
                fileName = "bordereau_" + timestamp + ".xml";
                String xmlPath = desktopPath + fileName;
                JasperExportManager.exportReportToXmlFile(jasperPrint, xmlPath, true);
                System.out.println("Fichier exporté à : " + xmlPath);
                break;

            case "excel":
                fileName = "bordereau_" + timestamp + ".xlsx";
                String excelPath = desktopPath + fileName;
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(excelPath));
                SimpleXlsxReportConfiguration xlsxConfiguration = new SimpleXlsxReportConfiguration();
                xlsxConfiguration.setDetectCellType(true);
                xlsxConfiguration.setOnePagePerSheet(false);
                xlsxConfiguration.setRemoveEmptySpaceBetweenRows(true);
                xlsxExporter.setConfiguration(xlsxConfiguration);
                xlsxExporter.exportReport();
                System.out.println("Fichier exporté à : " + excelPath);
                break;

            case "word":
                fileName = "bordereau_" + timestamp + ".docx";
                String wordPath = desktopPath + fileName;
                JRDocxExporter docxExporter = new JRDocxExporter();
                docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(wordPath));
                SimpleDocxReportConfiguration docxConfiguration = new SimpleDocxReportConfiguration();
                docxConfiguration.setFlexibleRowHeight(true);
                docxExporter.setConfiguration(docxConfiguration);
                docxExporter.exportReport();
                System.out.println("Fichier exporté à : " + wordPath);
                break;

            default:
                throw new IllegalArgumentException("Format de rapport non supporté: " + reportFormat);
        }

        return outputStream.toByteArray();
    }
}
