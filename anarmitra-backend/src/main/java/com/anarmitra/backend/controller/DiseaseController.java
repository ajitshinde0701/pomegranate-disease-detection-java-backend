package com.anarmitra.backend.controller;

import com.anarmitra.backend.entity.DiseaseRecord;
import com.anarmitra.backend.entity.DiseaseSuggestion;
import com.anarmitra.backend.repository.DiseaseSuggestionRepository;
import com.anarmitra.backend.service.DiseaseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disease")
public class DiseaseController {

    private final DiseaseService diseaseService;
    private final DiseaseSuggestionRepository suggestionRepository;

    public DiseaseController(
            DiseaseService diseaseService,
            DiseaseSuggestionRepository suggestionRepository
    ) {
        this.diseaseService = diseaseService;
        this.suggestionRepository = suggestionRepository;
    }

    @PostMapping("/detect")
    public Map detectDisease(
            @RequestParam Long farmerId,
            @RequestParam("file") MultipartFile file
    ) {
        return diseaseService.detectDisease(farmerId, file);
    }

    @GetMapping("/history")
    public List<DiseaseRecord> history(@RequestParam(required = false) Long farmerId) {
        return diseaseService.getHistory(farmerId);
    }

    @GetMapping("/suggestions/{diseaseName}")
    public DiseaseSuggestion getSuggestion(@PathVariable String diseaseName) {
        return suggestionRepository.findByDiseaseNameIgnoreCase(diseaseName)
                .orElseThrow(() -> new RuntimeException("No suggestion found for disease: " + diseaseName));
    }

    @PostMapping("/suggestions")
    public DiseaseSuggestion addSuggestion(@RequestBody DiseaseSuggestion suggestion) {
        return suggestionRepository.save(suggestion);
    }
}