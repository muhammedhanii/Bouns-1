package com.parking.smartparking.service;

import com.parking.smartparking.model.*;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Parking Service operations.
 */
public interface IParkingService {
    Ticket parkCar(String plate);
    Receipt exitCar(Long ticketId);
    Reservation reserveSpot(Long spotId, String userId, int hours);
    void cancelReservation(Long resId);
    ParkingStatus getParkingStatus();
    List<ParkingSpot> getAvailableSpots();
    Optional<Ticket> findTicketById(Long id);
}
