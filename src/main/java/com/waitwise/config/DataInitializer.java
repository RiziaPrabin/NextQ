package com.waitwise.config;

import com.waitwise.entity.Location;
import com.waitwise.entity.QueueStatus;
import com.waitwise.entity.ServiceHistory;
import com.waitwise.repository.LocationRepository;
import com.waitwise.repository.QueueStatusRepository;
import com.waitwise.repository.ServiceHistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            LocationRepository locationRepository,
            QueueStatusRepository queueStatusRepository,
            ServiceHistoryRepository serviceHistoryRepository) {

        return args -> {

            // Create locations and current queue status only once
            if (locationRepository.count() == 0) {

                Location hospital = new Location(
                        "City Hospital",
                        "Hospital",
                        "MG Road"
                );

                Location bank = new Location(
                        "Central Bank",
                        "Bank",
                        "Indiranagar"
                );

                Location college = new Location(
                        "Student Office",
                        "College",
                        "Kottayam"
                );

                locationRepository.save(hospital);
                locationRepository.save(bank);
                locationRepository.save(college);

                queueStatusRepository.save(
                        new QueueStatus(
                                12,
                                2,
                                8,
                                LocalDateTime.now(),
                                hospital
                        )
                );

                queueStatusRepository.save(
                        new QueueStatus(
                                5,
                                1,
                                6,
                                LocalDateTime.now(),
                                bank
                        )
                );

                queueStatusRepository.save(
                        new QueueStatus(
                                18,
                                3,
                                5,
                                LocalDateTime.now(),
                                college
                        )
                );
            }

            // Create historical data only once
            if (serviceHistoryRepository.count() == 0) {

                Location hospital =
                        locationRepository.findById(1L).orElseThrow();

                Location bank =
                        locationRepository.findById(2L).orElseThrow();

                Location college =
                        locationRepository.findById(3L).orElseThrow();

                // Hospital history
                serviceHistoryRepository.save(
                        new ServiceHistory(
                                8,
                                6,
                                LocalDateTime.now().minusDays(3),
                                hospital
                        )
                );

                serviceHistoryRepository.save(
                        new ServiceHistory(
                                15,
                                8,
                                LocalDateTime.now().minusDays(2),
                                hospital
                        )
                );

                serviceHistoryRepository.save(
                        new ServiceHistory(
                                22,
                                10,
                                LocalDateTime.now().minusDays(1),
                                hospital
                        )
                );

                // Bank history
                serviceHistoryRepository.save(
                        new ServiceHistory(
                                6,
                                5,
                                LocalDateTime.now().minusDays(3),
                                bank
                        )
                );

                serviceHistoryRepository.save(
                        new ServiceHistory(
                                10,
                                7,
                                LocalDateTime.now().minusDays(2),
                                bank
                        )
                );

                serviceHistoryRepository.save(
                        new ServiceHistory(
                                14,
                                8,
                                LocalDateTime.now().minusDays(1),
                                bank
                        )
                );

                // College history
                serviceHistoryRepository.save(
                        new ServiceHistory(
                                10,
                                4,
                                LocalDateTime.now().minusDays(3),
                                college
                        )
                );

                serviceHistoryRepository.save(
                        new ServiceHistory(
                                18,
                                5,
                                LocalDateTime.now().minusDays(2),
                                college
                        )
                );

                serviceHistoryRepository.save(
                        new ServiceHistory(
                                25,
                                6,
                                LocalDateTime.now().minusDays(1),
                                college
                        )
                );
            }
        };
    }
}