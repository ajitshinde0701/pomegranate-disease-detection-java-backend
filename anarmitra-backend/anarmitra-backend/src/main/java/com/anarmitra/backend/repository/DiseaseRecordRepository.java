package com.anarmitra.backend.repository;

import com.anarmitra.backend.entity.DiseaseRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRecordRepository
        extends JpaRepository<DiseaseRecord, Long> {

    List<DiseaseRecord> findByFarmerIdOrderByDetectedAtDesc(
            Long farmerId
    );
}