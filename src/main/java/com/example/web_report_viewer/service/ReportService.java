package com.example.web_report_viewer.service;

import com.example.web_report_viewer.entities.TransmissionSheet;
import com.example.web_report_viewer.repository.TransmissionSheetRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TransmissionSheetRepository transmissionSheetRepository;

    public byte[] exportReport(String reportFormat, Long sheetId) throws JRException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TransmissionSheet sheet= transmissionSheetRepository.findById(sheetId)
                .orElseThrow(()-> new IllegalArgumentException("Bordereau d'expédition inexistant pour l'id: "+sheetId));

        InputStream reportStream = getClass().getResourceAsStream("/reports/transmissionSheet.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource couriersDataSource = new JRBeanCollectionDataSource(sheet.getCouriers());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("provenance", sheet.getProvenance());
        parameters.put("destination", sheet.getDestination());
        parameters.put("emissionDate", sheet.getEmissionDate());
        parameters.put("courrierCount", sheet.getCourrierCount());
        parameters.put("couriersDataSource", couriersDataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        if(reportFormat.equalsIgnoreCase("pdf")){
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } else if (reportFormat.equalsIgnoreCase("excel")){
            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return outputStream.toByteArray();
        } else if (reportFormat.equalsIgnoreCase("word")) {
            JRDocxExporter exporter = new JRDocxExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

            SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
            configuration.setFlexibleRowHeight(true);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
            return outputStream.toByteArray();
        }

        return null;
    }

}
