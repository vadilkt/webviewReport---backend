package com.example.web_report_viewer.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class TransmissionSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provenance;
    private String destination;
    private Date emissionDate;
    private Integer courrierCount;

    @OneToMany(mappedBy = "transmissionSheet", cascade = CascadeType.ALL)
    private List<Courier> couriers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }

    public Integer getCourrierCount() {
        return courrierCount;
    }

    public void setCourrierCount(Integer courrierCount) {
        this.courrierCount = courrierCount;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }
}
