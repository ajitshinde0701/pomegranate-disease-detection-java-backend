package com.anarmitra.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "disease_records")
public class DiseaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long farmerId;
    private String diseaseName;
    private Double confidence;
    private String imageName;

    @Column(length = 3000)
    private String resultJson;

    private LocalDateTime detectedAt = LocalDateTime.now();

    public DiseaseRecord() {}

    public Long getId() {
        return id;
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public Double getConfidence() {
        return confidence;
    }

    public String getImageName() {
        return imageName;
    }

    public String getResultJson() {
        return resultJson;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
}