//This will store past service/waiting information, which we can later use for the Crowd Insights section.
package com.waitwise.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_history")
public class ServiceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int peopleServed;

    private double serviceDuration;

    private LocalDateTime servedAt;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public ServiceHistory() {
    }

    public ServiceHistory(int peopleServed, double serviceDuration,
                          LocalDateTime servedAt, Location location) {
        this.peopleServed = peopleServed;
        this.serviceDuration = serviceDuration;
        this.servedAt = servedAt;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public int getPeopleServed() {
        return peopleServed;
    }

    public void setPeopleServed(int peopleServed) {
        this.peopleServed = peopleServed;
    }

    public double getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(double serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public LocalDateTime getServedAt() {
        return servedAt;
    }

    public void setServedAt(LocalDateTime servedAt) {
        this.servedAt = servedAt;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}