package com.parking.smartparking.repository;

import com.parking.smartparking.model.ParkingSpot;
import com.parking.smartparking.model.Reservation;
import com.parking.smartparking.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Parking Repository operations.
 */
public interface IParkingRepository {
    void save(ParkingSpot spot);
    Optional<ParkingSpot> findSpotById(Long id);
    List<ParkingSpot> findAllSpots();
    Long saveReservation(Reservation res);
    Optional<Reservation> findReservationById(Long id);
    void deleteReservation(Long id);
    Long saveTicket(Ticket ticket);
    Optional<Ticket> findTicketById(Long id);
    void deleteTicket(Long id);
    Optional<ParkingSpot> findAvailableSpot();
    void updateSpotStatus(Long spotId, boolean isAvailable);
    int countReservations();
}
