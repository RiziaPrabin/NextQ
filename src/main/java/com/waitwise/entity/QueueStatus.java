//it stores the current queue information for each location.
package com.waitwise.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_status")
public class QueueStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int peopleWaiting;

    private int currentlyServing;

    private double averageServiceTime;

    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public QueueStatus() {
    }

    public QueueStatus(int peopleWaiting, int currentlyServing,
                       double averageServiceTime, LocalDateTime updatedAt,
                       Location location) {
        this.peopleWaiting = peopleWaiting;
        this.currentlyServing = currentlyServing;
        this.averageServiceTime = averageServiceTime;
        this.updatedAt = updatedAt;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public int getPeopleWaiting() {
        return peopleWaiting;
    }

    public void setPeopleWaiting(int peopleWaiting) {
        this.peopleWaiting = peopleWaiting;
    }

    public int getCurrentlyServing() {
        return currentlyServing;
    }

    public void setCurrentlyServing(int currentlyServing) {
        this.currentlyServing = currentlyServing;
    }

    public double getAverageServiceTime() {
        return averageServiceTime;
    }

    public void setAverageServiceTime(double averageServiceTime) {
        this.averageServiceTime = averageServiceTime;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}