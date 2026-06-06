package com.anarmitra.backend.repository;

import com.anarmitra.backend.entity.FarmerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmerRequestRepository extends JpaRepository<FarmerRequest, Long> {
    List<FarmerRequest> findByReceiverId(Long receiverId);
    List<FarmerRequest> findByFarmerId(Long farmerId);
}