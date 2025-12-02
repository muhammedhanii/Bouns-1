package com.parking.smartparking.repository;

import com.parking.smartparking.model.ParkingSpot;
import com.parking.smartparking.model.Reservation;
import com.parking.smartparking.model.Ticket;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository implementation for Parking data operations.
 * Uses in-memory storage for demonstration purposes.
 */
@Repository
public class ParkingRepository implements IParkingRepository {
    private final List<ParkingSpot> spotDatabase = new ArrayList<>();
    private final List<Reservation> reservationDatabase = new ArrayList<>();
    private final List<Ticket> ticketDatabase = new ArrayList<>();
    
    private final AtomicLong spotIdGenerator = new AtomicLong(1);
    private final AtomicLong reservationIdGenerator = new AtomicLong(1);
    private final AtomicLong ticketIdGenerator = new AtomicLong(1);

    @PostConstruct
    public void initializeSpots() {
        // Initialize 50 parking spots
        for (int i = 1; i <= 50; i++) {
            ParkingSpot spot = new ParkingSpot(spotIdGenerator.getAndIncrement(), "SPOT-" + String.format("%03d", i));
            spotDatabase.add(spot);
        }
    }

    @Override
    public void save(ParkingSpot spot) {
        Optional<ParkingSpot> existing = findSpotById(spot.getSpotId());
        if (existing.isPresent()) {
            int index = spotDatabase.indexOf(existing.get());
            spotDatabase.set(index, spot);
        } else {
            if (spot.getSpotId() == null) {
                spot.setSpotId(spotIdGenerator.getAndIncrement());
            }
            spotDatabase.add(spot);
        }
    }

    @Override
    public Optional<ParkingSpot> findSpotById(Long id) {
        return spotDatabase.stream()
                .filter(spot -> spot.getSpotId().equals(id))
                .findFirst();
    }

    @Override
    public List<ParkingSpot> findAllSpots() {
        return new ArrayList<>(spotDatabase);
    }

    @Override
    public Long saveReservation(Reservation res) {
        if (res.getReservationId() == null) {
            res.setReservationId(reservationIdGenerator.getAndIncrement());
        }
        reservationDatabase.add(res);
        return res.getReservationId();
    }

    @Override
    public Optional<Reservation> findReservationById(Long id) {
        return reservationDatabase.stream()
                .filter(res -> res.getReservationId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteReservation(Long id) {
        reservationDatabase.removeIf(res -> res.getReservationId().equals(id));
    }

    @Override
    public Long saveTicket(Ticket ticket) {
        if (ticket.getTicketId() == null) {
            ticket.setTicketId(ticketIdGenerator.getAndIncrement());
        }
        ticketDatabase.add(ticket);
        return ticket.getTicketId();
    }

    @Override
    public Optional<Ticket> findTicketById(Long id) {
        return ticketDatabase.stream()
                .filter(ticket -> ticket.getTicketId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteTicket(Long id) {
        ticketDatabase.removeIf(ticket -> ticket.getTicketId().equals(id));
    }

    @Override
    public Optional<ParkingSpot> findAvailableSpot() {
        return spotDatabase.stream()
                .filter(ParkingSpot::isAvailable)
                .findFirst();
    }

    @Override
    public void updateSpotStatus(Long spotId, boolean isAvailable) {
        findSpotById(spotId).ifPresent(spot -> {
            if (isAvailable) {
                spot.release();
            } else {
                spot.setOccupied(true);
            }
        });
    }

    @Override
    public int countReservations() {
        return reservationDatabase.size();
    }
}
