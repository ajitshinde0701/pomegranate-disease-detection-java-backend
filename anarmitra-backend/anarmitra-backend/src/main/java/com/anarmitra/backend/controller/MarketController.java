package com.anarmitra.backend.controller;

import com.anarmitra.backend.entity.MarketData;
import com.anarmitra.backend.service.MarketService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @PostMapping("/upload-csv")
    public Map<String, String> uploadCsv(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        response.put("message", marketService.uploadCsv(file));
        return response;
    }

    @GetMapping
    public List<MarketData> getMarketData(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String market,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String variety
    ) {
        return marketService.getMarketData(year, market, district, variety);
    }

    @GetMapping("/predict")
    public Map<String, Object> predictPrice(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String market,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String variety
    ) {
        double prediction = marketService.predictPrice(year, market, district, variety);

        Map<String, Object> response = new HashMap<>();
        response.put("predictedPrice", prediction);
        response.put("message", "Prediction generated from uploaded CSV/database market data");

        return response;
    }
}