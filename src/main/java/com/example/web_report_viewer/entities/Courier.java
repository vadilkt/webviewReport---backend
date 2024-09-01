package com.example.web_report_viewer.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courrierNumber;
    private String sender;
    private String subject;
    private Date courrierDate;
    private String dossierNumber;

    @ManyToOne
    @JoinColumn(name="transmission_sheet_id")
    private TransmissionSheet transmissionSheet;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourrierNumber() {
        return courrierNumber;
    }

    public void setCourrierNumber(String courrierNumber) {
        this.courrierNumber = courrierNumber;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCourrierDate() {
        return courrierDate;
    }

    public void setCourrierDate(Date courrierDate) {
        this.courrierDate = courrierDate;
    }

    public String getDossierNumber() {
        return dossierNumber;
    }

    public void setDossierNumber(String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }

    public TransmissionSheet getTransmissionSheet() {
        return transmissionSheet;
    }

    public void setTransmissionSheet(TransmissionSheet transmissionSheet) {
        this.transmissionSheet = transmissionSheet;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
