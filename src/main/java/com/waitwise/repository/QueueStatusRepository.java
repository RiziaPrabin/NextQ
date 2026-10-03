package com.waitwise.repository;

import com.waitwise.entity.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueStatusRepository extends JpaRepository<QueueStatus, Long> {

    Optional<QueueStatus> findByLocationId(Long locationId);
}