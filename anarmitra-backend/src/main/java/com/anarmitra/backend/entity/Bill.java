package com.anarmitra.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long farmerId;
    private Long sellerId;
    private String sellerType;

    private Double totalAmount;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    private List<BillItem> items;

    public Bill() {}

    public Long getId() {
        return id;
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getSellerType() {
        return sellerType;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }
}