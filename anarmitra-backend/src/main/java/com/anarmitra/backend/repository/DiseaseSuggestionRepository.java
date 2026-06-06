package com.anarmitra.backend.repository;

import com.anarmitra.backend.entity.DiseaseSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiseaseSuggestionRepository extends JpaRepository<DiseaseSuggestion, Long> {
    Optional<DiseaseSuggestion> findByDiseaseNameIgnoreCase(String diseaseName);
}