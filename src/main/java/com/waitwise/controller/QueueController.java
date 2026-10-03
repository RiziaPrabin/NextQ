package com.waitwise.controller;

import com.waitwise.entity.QueueStatus;
import com.waitwise.repository.QueueStatusRepository;
import com.waitwise.service.WaitTimeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final WaitTimeService waitTimeService;
    private final QueueStatusRepository queueStatusRepository;

    public QueueController(WaitTimeService waitTimeService,
                           QueueStatusRepository queueStatusRepository) {
        this.waitTimeService = waitTimeService;
        this.queueStatusRepository = queueStatusRepository;
    }

    @GetMapping("/{locationId}")
    public QueueResponse getQueueStatus(@PathVariable Long locationId) {

        QueueStatus queueStatus = queueStatusRepository
                .findByLocationId(locationId)
                .orElseThrow(() ->
                        new RuntimeException("Queue status not found"));

        double estimatedWaitTime =
                waitTimeService.calculateWaitTime(locationId);

        String crowdStatus;

        if (queueStatus.getPeopleWaiting() >= 15) {
            crowdStatus = "HIGH";
        } else if (queueStatus.getPeopleWaiting() >= 8) {
            crowdStatus = "MEDIUM";
        } else {
            crowdStatus = "LOW";
        }

        return new QueueResponse(
                queueStatus.getPeopleWaiting(),
                queueStatus.getCurrentlyServing(),
                queueStatus.getAverageServiceTime(),
                estimatedWaitTime,
                crowdStatus,
                queueStatus.getUpdatedAt()
        );
    }

    public record QueueResponse(
            int peopleWaiting,
            int currentlyServing,
            double averageServiceTime,
            double estimatedWaitTime,
            String crowdStatus,
            java.time.LocalDateTime updatedAt
    ) {
    }
}