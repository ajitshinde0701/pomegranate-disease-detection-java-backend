package com.anarmitra.backend.service;

import com.anarmitra.backend.entity.MarketData;
import com.anarmitra.backend.repository.MarketDataRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MarketService {

    private final MarketDataRepository marketRepository;

    public MarketService(MarketDataRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public String uploadCsv(MultipartFile file) {
        try (
                Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                CSVParser csvParser = new CSVParser(
                        reader,
                        CSVFormat.DEFAULT
                                .builder()
                                .setHeader()
                                .setSkipHeaderRecord(true)
                                .setTrim(true)
                                .build()
                )
        ) {
            for (CSVRecord record : csvParser) {
                MarketData data = new MarketData();

                data.setYear(Integer.parseInt(record.get("year")));
                data.setMarket(record.get("market"));
                data.setDistrict(record.get("district"));
                data.setVariety(record.get("variety"));
                data.setMinPrice(Double.parseDouble(record.get("minPrice")));
                data.setMaxPrice(Double.parseDouble(record.get("maxPrice")));
                data.setModalPrice(Double.parseDouble(record.get("modalPrice")));
                data.setArrivals(Double.parseDouble(record.get("arrivals")));

                marketRepository.save(data);
            }

            return "CSV uploaded and saved successfully";

        } catch (Exception e) {
            throw new RuntimeException("CSV upload failed: " + e.getMessage());
        }
    }

    public List<MarketData> getMarketData(Integer year, String market, String district, String variety) {
        market = market == null ? "" : market;
        district = district == null ? "" : district;
        variety = variety == null ? "" : variety;

        if (year != null) {
            return marketRepository
                    .findByYearAndMarketContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndVarietyContainingIgnoreCase(
                            year, market, district, variety
                    );
        }

        return marketRepository
                .findByMarketContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndVarietyContainingIgnoreCase(
                        market, district, variety
                );
    }

    public double predictPrice(Integer year, String market, String district, String variety) {
        List<MarketData> data = getMarketData(year, market, district, variety);

        if (data.isEmpty()) {
            throw new RuntimeException("No market data found for prediction");
        }

        double sum = 0;

        for (MarketData item : data) {
            sum += item.getModalPrice();
        }

        return sum / data.size();
    }
}