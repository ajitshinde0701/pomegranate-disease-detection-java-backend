package com.anarmitra.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class DiseaseDetectionService {

    @Value("${ml.api.url}")
    private String mlApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> detectDisease(MultipartFile file) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add(
                "file",
                new MultipartInputStreamFileResource(
                        file.getInputStream(),
                        file.getOriginalFilename()
                )
        );

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                mlApiUrl + "/predict",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        return response.getBody();
    }

    private static class MultipartInputStreamFileResource
            extends InputStreamResource {

        private final String filename;

        public MultipartInputStreamFileResource(
                java.io.InputStream inputStream,
                String filename
        ) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() {
            return -1;
        }
    }
}