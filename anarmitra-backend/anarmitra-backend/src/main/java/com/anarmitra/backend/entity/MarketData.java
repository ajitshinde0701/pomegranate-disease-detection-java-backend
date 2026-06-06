package com.anarmitra.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "market_data")
public class MarketData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;
    private String market;
    private String district;
    private String variety;

    private Double minPrice;
    private Double maxPrice;
    private Double modalPrice;
    private Double arrivals;

    public MarketData() {}

    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getMarket() {
        return market;
    }

    public String getDistrict() {
        return district;
    }

    public String getVariety() {
        return variety;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Double getModalPrice() {
        return modalPrice;
    }

    public Double getArrivals() {
        return arrivals;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setModalPrice(Double modalPrice) {
        this.modalPrice = modalPrice;
    }

    public void setArrivals(Double arrivals) {
        this.arrivals = arrivals;
    }
}