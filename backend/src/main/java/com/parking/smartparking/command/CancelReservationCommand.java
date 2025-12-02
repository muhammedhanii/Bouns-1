package com.parking.smartparking.command;

import com.parking.smartparking.service.IParkingService;

/**
 * Concrete Command for canceling a reservation.
 */
public class CancelReservationCommand implements Command {
    private final IParkingService parkingService;
    private final Long reservationId;
    private boolean success;

    public CancelReservationCommand(IParkingService parkingService, Long reservationId) {
        this.parkingService = parkingService;
        this.reservationId = reservationId;
    }

    @Override
    public Boolean execute() {
        try {
            parkingService.cancelReservation(reservationId);
            success = true;
            return true;
        } catch (Exception e) {
            success = false;
            return false;
        }
    }

    @Override
    public void undo() {
        // Cancel operation cannot be easily undone
        // Would need to re-create the reservation with original data
        System.out.println("Cancel reservation operation cannot be undone");
    }

    public Long getReservationId() {
        return reservationId;
    }

    public boolean isSuccess() {
        return success;
    }
}
