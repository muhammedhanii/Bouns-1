package com.parking.smartparking.command;

import com.parking.smartparking.model.Reservation;
import com.parking.smartparking.service.IParkingService;

/**
 * Concrete Command for reserving a parking spot.
 */
public class ReserveSpotCommand implements Command {
    private final IParkingService parkingService;
    private final Long spotId;
    private final String userId;
    private final int hours;
    private Reservation createdReservation;

    public ReserveSpotCommand(IParkingService parkingService, Long spotId, String userId, int hours) {
        this.parkingService = parkingService;
        this.spotId = spotId;
        this.userId = userId;
        this.hours = hours;
    }

    @Override
    public Reservation execute() {
        createdReservation = parkingService.reserveSpot(spotId, userId, hours);
        return createdReservation;
    }

    @Override
    public void undo() {
        if (createdReservation != null) {
            parkingService.cancelReservation(createdReservation.getReservationId());
        }
    }

    public Long getSpotId() {
        return spotId;
    }

    public String getUserId() {
        return userId;
    }

    public int getHours() {
        return hours;
    }
}
