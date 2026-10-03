package com.waitwise.controller;

import com.waitwise.entity.ServiceHistory;
import com.waitwise.repository.ServiceHistoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final ServiceHistoryRepository serviceHistoryRepository;

    public HistoryController(
            ServiceHistoryRepository serviceHistoryRepository) {
        this.serviceHistoryRepository = serviceHistoryRepository;
    }

    @GetMapping("/{locationId}")
    public List<ServiceHistory> getHistory(
            @PathVariable Long locationId) {

        return serviceHistoryRepository
                .findByLocationId(locationId);
    }
}