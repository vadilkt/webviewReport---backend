package com.example.web_report_viewer.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class TransmissionSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_emission")
    private Date dateEmission;

    @Column(name = "destination")
    private String destination;

    @Column(name = "nbre_courrier")
    private Integer nbreCourrier;

    @Column(name = "num_bordereau")
    private String numBordereau;

    @Column(name = "provenance")
    private String provenance;
    @OneToMany(mappedBy = "transmissionSheet", cascade = CascadeType.ALL)
    private List<Courier> couriers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumBordereau() {
        return numBordereau;
    }

    public void setNumBordereau(String numBordereau) {
        this.numBordereau = numBordereau;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNbreCourrier() {
        return nbreCourrier;
    }

    public void setNbreCourrier(int nbreCourrier) {
        this.nbreCourrier = nbreCourrier;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }
}
