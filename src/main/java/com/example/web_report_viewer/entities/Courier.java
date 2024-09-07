package com.example.web_report_viewer.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numCourrier;
    private String expediteur;
    private String objet;
    private Date courrierDate;
    private String dossierNumber;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
    private List<Attachment> attachments;


    @ManyToOne
    @JoinColumn(name="transmission_sheet_id")
    TransmissionSheet transmissionSheet;

    public TransmissionSheet getTransmissionSheet() {
        return transmissionSheet;
    }

    public void setTransmissionSheet(TransmissionSheet transmissionSheet) {
        this.transmissionSheet = transmissionSheet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCourrier() {
        return numCourrier;
    }

    public void setNumCourrier(String numCourrier) {
        this.numCourrier = numCourrier;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
