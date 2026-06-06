package com.anarmitra.backend.repository;

import com.anarmitra.backend.entity.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {

    List<MarketData> findByYearAndMarketContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndVarietyContainingIgnoreCase(
            Integer year,
            String market,
            String district,
            String variety
    );

    List<MarketData> findByMarketContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndVarietyContainingIgnoreCase(
            String market,
            String district,
            String variety
    );
}