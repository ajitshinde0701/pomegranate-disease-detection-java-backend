package com.anarmitra.backend.controller;

import com.anarmitra.backend.entity.FarmerRequest;
import com.anarmitra.backend.repository.FarmerRequestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class FarmerRequestController {

    private final FarmerRequestRepository requestRepository;

    public FarmerRequestController(FarmerRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @PostMapping
    public FarmerRequest createRequest(@RequestBody FarmerRequest request) {
        request.setStatus("PENDING");
        return requestRepository.save(request);
    }

    @GetMapping("/receiver/{receiverId}")
    public List<FarmerRequest> getRequestsForReceiver(@PathVariable Long receiverId) {
        return requestRepository.findByReceiverId(receiverId);
    }

    @GetMapping("/farmer/{farmerId}")
    public List<FarmerRequest> getRequestsByFarmer(@PathVariable Long farmerId) {
        return requestRepository.findByFarmerId(farmerId);
    }

    @PutMapping("/{id}/status")
    public FarmerRequest updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        FarmerRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status);
        return requestRepository.save(request);
    }
}