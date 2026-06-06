package com.anarmitra.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "disease_suggestions")
public class DiseaseSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diseaseName;

    @Column(length = 3000)
    private String treatment;

    @Column(length = 3000)
    private String fertilizerRecommendation;

    @Column(length = 3000)
    private String pesticideRecommendation;

    public DiseaseSuggestion() {}

    public Long getId() {
        return id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getFertilizerRecommendation() {
        return fertilizerRecommendation;
    }

    public String getPesticideRecommendation() {
        return pesticideRecommendation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void setFertilizerRecommendation(String fertilizerRecommendation) {
        this.fertilizerRecommendation = fertilizerRecommendation;
    }

    public void setPesticideRecommendation(String pesticideRecommendation) {
        this.pesticideRecommendation = pesticideRecommendation;
    }
}