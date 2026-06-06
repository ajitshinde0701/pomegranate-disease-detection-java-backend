package com.anarmitra.backend.repository;

import com.anarmitra.backend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByFarmerId(Long farmerId);
    List<Bill> findBySellerId(Long sellerId);
}