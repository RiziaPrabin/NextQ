package com.waitwise.service;

import com.waitwise.entity.ServiceHistory;
import com.waitwise.repository.ServiceHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BestTimeService {

    private final ServiceHistoryRepository serviceHistoryRepository;

    public BestTimeService(ServiceHistoryRepository serviceHistoryRepository) {
        this.serviceHistoryRepository = serviceHistoryRepository;
    }

    public BestTimeResponse findBestTime(Long locationId) {

        List<ServiceHistory> history =
                serviceHistoryRepository.findByLocationId(locationId);

        if (history.isEmpty()) {
            return new BestTimeResponse(
                    "Not enough data",
                    0,
                    0,
                    false,
                    "No service history is available yet."
            );
        }

        Map<String, List<ServiceHistory>> groupedHistory =
                history.stream()
                        .collect(Collectors.groupingBy(
                                record -> getTimePeriod(
                                        record.getServedAt().toLocalTime()
                                )
                        ));

        String bestPeriod = null;
        double bestAverage = Double.MAX_VALUE;
        int bestRecordCount = 0;

        for (Map.Entry<String, List<ServiceHistory>> entry
                : groupedHistory.entrySet()) {

            List<ServiceHistory> records = entry.getValue();

            double totalDuration = records.stream()
                    .mapToDouble(ServiceHistory::getServiceDuration)
                    .sum();

            int totalPeople = records.stream()
                    .mapToInt(ServiceHistory::getPeopleServed)
                    .sum();

            if (totalPeople == 0) {
                continue;
            }

            double averageMinutesPerPerson =
                    totalDuration / totalPeople;

            if (averageMinutesPerPerson < bestAverage) {
                bestAverage = averageMinutesPerPerson;
                bestPeriod = entry.getKey();
                bestRecordCount = records.size();
            }
        }

        if (bestPeriod == null) {
            return new BestTimeResponse(
                    "Not enough data",
                    0,
                    0,
                    false,
                    "The available service records are insufficient."
            );
        }

        boolean enoughData = bestRecordCount >= 2;

        String message;

        if (enoughData) {
            message = "Based on historical service activity, this period has shown faster service.";
        } else {
            message = "A preliminary recommendation based on limited historical data.";
        }

        return new BestTimeResponse(
                bestPeriod,
                Math.round(bestAverage * 100.0) / 100.0,
                bestRecordCount,
                enoughData,
                message
        );
    }

    private String getTimePeriod(LocalTime time) {

        if (time.isAfter(LocalTime.MIDNIGHT)
                && time.isBefore(LocalTime.NOON)) {

            return "Morning";
        }

        if (time.equals(LocalTime.NOON)
                || (time.isAfter(LocalTime.NOON)
                && time.isBefore(LocalTime.of(16, 0)))) {

            return "Afternoon";
        }

        if (time.equals(LocalTime.of(16, 0))
                || (time.isAfter(LocalTime.of(16, 0))
                && time.isBefore(LocalTime.of(20, 0)))) {

            return "Evening";
        }

        return "Night";
    }

    public record BestTimeResponse(
            String bestTimePeriod,
            double averageMinutesPerPerson,
            int recordsUsed,
            boolean enoughData,
            String message
    ) {}
}