package com.parking.smartparking.model;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Model class representing a Parking Ticket in the parking system.
 */
public class Ticket {
    private Long ticketId;
    private String plateNumber;
    private Long spotId;
    private LocalDateTime entryTime;

    public Ticket() {}

    public Ticket(Long ticketId, String plateNumber, Long spotId, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.plateNumber = plateNumber;
        this.spotId = spotId;
        this.entryTime = entryTime;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public String getTicketDetails() {
        return String.format("Ticket[id=%d, plate=%s, spot=%d, entry=%s]", 
            ticketId, plateNumber, spotId, entryTime);
    }

    public long getDuration() {
        return Duration.between(entryTime, LocalDateTime.now()).toMinutes();
    }
}
