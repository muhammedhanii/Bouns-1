package com.parking.smartparking.model;

import java.time.LocalDateTime;

/**
 * Model class representing a Reservation in the parking system.
 */
public class Reservation {
    private Long reservationId;
    private Long spotId;
    private String userId;
    private LocalDateTime reservedUntil;

    public Reservation() {}

    public Reservation(Long reservationId, Long spotId, String userId, LocalDateTime reservedUntil) {
        this.reservationId = reservationId;
        this.spotId = spotId;
        this.userId = userId;
        this.reservedUntil = reservedUntil;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(LocalDateTime reservedUntil) {
        this.reservedUntil = reservedUntil;
    }

    public boolean isActive() {
        return reservedUntil.isAfter(LocalDateTime.now());
    }

    public boolean isExpired() {
        return reservedUntil.isBefore(LocalDateTime.now());
    }

    public String getReservationDetails() {
        return String.format("Reservation[id=%d, spot=%d, user=%s, until=%s]",
            reservationId, spotId, userId, reservedUntil);
    }
}
