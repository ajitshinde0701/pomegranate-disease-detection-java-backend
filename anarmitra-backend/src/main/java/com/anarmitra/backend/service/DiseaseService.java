package com.anarmitra.backend.service;

import com.anarmitra.backend.entity.DiseaseRecord;
import com.anarmitra.backend.repository.DiseaseRecordRepository;
import com.anarmitra.backend.repository.DiseaseSuggestionRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DiseaseService {

    private final DiseaseRecordRepository diseaseRecordRepository;
    private final DiseaseSuggestionRepository suggestionRepository;

    @Value("${ml.api.url}")
    private String mlApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public DiseaseService(
            DiseaseRecordRepository diseaseRecordRepository,
            DiseaseSuggestionRepository suggestionRepository
    ) {
        this.diseaseRecordRepository = diseaseRecordRepository;
        this.suggestionRepository = suggestionRepository;
    }

    public Map<String, Object> detectDisease(
            Long farmerId,
            MultipartFile file
    ) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            body.add(
                    "file",
                    new MultipartInputStreamFileResource(
                            file.getInputStream(),
                            file.getOriginalFilename()
                    )
            );

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(
                            mlApiUrl + "/predict",
                            HttpMethod.POST,
                            requestEntity,
                            Map.class
                    );

            Map<String, Object> result = response.getBody();

            String disease =
                    result.get("disease").toString();

            Double confidence =
                    Double.parseDouble(
                            result.get("confidence").toString()
                    );

            DiseaseRecord record = new DiseaseRecord();

            record.setFarmerId(farmerId);

            record.setDiseaseName(disease);

            record.setConfidence(confidence);

            record.setImageName(
                    file.getOriginalFilename()
            );

            record.setResultJson(
                    result.toString()
            );

            record.setDetectedAt(
                    LocalDateTime.now()
            );

            diseaseRecordRepository.save(record);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return Map.of(
                    "error",
                    "Disease detection failed"
            );
        }
    }

    public List<DiseaseRecord> getHistory(Long farmerId) {

        if (farmerId != null) {

            return diseaseRecordRepository
                    .findByFarmerIdOrderByDetectedAtDesc(farmerId);
        }

        return diseaseRecordRepository.findAll();
    }

    private static class MultipartInputStreamFileResource
            extends InputStreamResource {

        private final String filename;

        public MultipartInputStreamFileResource(
                InputStream inputStream,
                String filename
        ) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return filename;
        }

        @Override
        public long contentLength() {
            return -1;
        }
    }
}