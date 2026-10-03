package com.waitwise.controller;

import com.waitwise.service.BestTimeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/best-time")
public class BestTimeController {

    private final BestTimeService bestTimeService;

    public BestTimeController(BestTimeService bestTimeService) {
        this.bestTimeService = bestTimeService;
    }

    @GetMapping("/{locationId}")
    public BestTimeService.BestTimeResponse getBestTime(
            @PathVariable Long locationId) {

        return bestTimeService.findBestTime(locationId);
    }
}