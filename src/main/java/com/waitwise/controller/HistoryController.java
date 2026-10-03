package com.waitwise.controller;

import com.waitwise.entity.Location;
import com.waitwise.entity.ServiceHistory;
import com.waitwise.repository.LocationRepository;
import com.waitwise.repository.ServiceHistoryRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final ServiceHistoryRepository serviceHistoryRepository;
    private final LocationRepository locationRepository;

    public HistoryController(
            ServiceHistoryRepository serviceHistoryRepository,
            LocationRepository locationRepository) {

        this.serviceHistoryRepository = serviceHistoryRepository;
        this.locationRepository = locationRepository;
    }


    @GetMapping("/{locationId}")
    public List<ServiceHistory> getHistory(
            @PathVariable Long locationId) {

        return serviceHistoryRepository
                .findByLocationId(locationId);
    }


    @PostMapping("/{locationId}")
    public ServiceHistory recordService(
            @PathVariable Long locationId,
            @RequestBody ServiceHistoryRequest request) {

        Location location =
                locationRepository.findById(locationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Location not found"
                                ));

        ServiceHistory history =
                new ServiceHistory(
                        request.peopleServed(),
                        request.serviceDuration(),
                        LocalDateTime.now(),
                        location
                );

        return serviceHistoryRepository.save(history);
    }


    public record ServiceHistoryRequest(
            int peopleServed,
            double serviceDuration
    ) {}
}