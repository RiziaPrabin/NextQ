package com.waitwise.service;

import com.waitwise.entity.QueueStatus;
import com.waitwise.repository.QueueStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class WaitTimeService {

    private final QueueStatusRepository queueStatusRepository;

    public WaitTimeService(QueueStatusRepository queueStatusRepository) {
        this.queueStatusRepository = queueStatusRepository;
    }

    public double calculateWaitTime(Long locationId) {

        QueueStatus queueStatus = queueStatusRepository
                .findByLocationId(locationId)
                .orElseThrow(() ->
                        new RuntimeException("Queue status not found"));

        return queueStatus.getPeopleWaiting()
                * queueStatus.getAverageServiceTime();
    }
}