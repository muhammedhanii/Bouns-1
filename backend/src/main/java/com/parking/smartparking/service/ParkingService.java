package com.parking.smartparking.service;

import com.parking.smartparking.hardware.LEDDisplay;
import com.parking.smartparking.hardware.ParkingGate;
import com.parking.smartparking.hardware.ParkingSensor;
import com.parking.smartparking.model.*;
import com.parking.smartparking.repository.IParkingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Service implementation for Parking operations.
 * Acts as the Receiver in the Command Pattern.
 */
@Service
public class ParkingService implements IParkingService {
    private final IParkingRepository parkingRepository;
    private final ParkingGate parkingGate;
    private final ParkingSensor parkingSensor;
    private final LEDDisplay ledDisplay;
    private final AtomicLong receiptIdGenerator = new AtomicLong(1);

    public ParkingService(IParkingRepository parkingRepository, 
                          ParkingGate parkingGate,
                          ParkingSensor parkingSensor, 
                          LEDDisplay ledDisplay) {
        this.parkingRepository = parkingRepository;
        this.parkingGate = parkingGate;
        this.parkingSensor = parkingSensor;
        this.ledDisplay = ledDisplay;
    }

    @Override
    public Ticket parkCar(String plate) {
        // Find an available spot
        Optional<ParkingSpot> availableSpot = parkingRepository.findAvailableSpot();
        
        if (availableSpot.isEmpty()) {
            throw new RuntimeException("No available parking spots");
        }

        ParkingSpot spot = availableSpot.get();
        
        // Create a car and occupy the spot
        Car car = new Car(System.currentTimeMillis(), plate, "Unknown");
        spot.occupy(car);
        parkingRepository.save(spot);

        // Create and save ticket
        Ticket ticket = new Ticket(null, plate, spot.getSpotId(), LocalDateTime.now());
        parkingRepository.saveTicket(ticket);

        // Open the gate
        parkingGate.openGate();

        // Update LED display with available count
        int availableCount = (int) parkingRepository.findAllSpots().stream()
                .filter(ParkingSpot::isAvailable).count();
        ledDisplay.updateDisplay(availableCount);

        return ticket;
    }

    @Override
    public Receipt exitCar(Long ticketId) {
        // Find the ticket
        Optional<Ticket> ticketOpt = parkingRepository.findTicketById(ticketId);
        
        if (ticketOpt.isEmpty()) {
            throw new RuntimeException("Ticket not found: " + ticketId);
        }

        Ticket ticket = ticketOpt.get();
        
        // Calculate duration in hours (rounded up, minimum 1 hour)
        long minutes = Duration.between(ticket.getEntryTime(), LocalDateTime.now()).toMinutes();
        double hoursDecimal = (double) minutes / 60.0;
        double hours = Math.max(1.0, Math.ceil(hoursDecimal));
        
        // Calculate fee ($2 per hour, minimum $5)
        double amount = Math.max(5.0, hours * 2.0);

        // Update spot status to available
        parkingRepository.updateSpotStatus(ticket.getSpotId(), true);

        // Create receipt
        Receipt receipt = new Receipt(
            receiptIdGenerator.getAndIncrement(),
            ticketId,
            hours,
            amount,
            LocalDateTime.now()
        );

        // Delete the ticket
        parkingRepository.deleteTicket(ticketId);

        // Open the gate
        parkingGate.openGate();

        // Update LED display
        int availableCount = (int) parkingRepository.findAllSpots().stream()
                .filter(ParkingSpot::isAvailable).count();
        ledDisplay.updateDisplay(availableCount);

        return receipt;
    }

    @Override
    public Reservation reserveSpot(Long spotId, String userId, int hours) {
        // Find the spot
        Optional<ParkingSpot> spotOpt = parkingRepository.findSpotById(spotId);
        
        if (spotOpt.isEmpty()) {
            throw new RuntimeException("Spot not found: " + spotId);
        }

        ParkingSpot spot = spotOpt.get();
        
        if (!spot.isAvailable()) {
            throw new RuntimeException("Spot is not available: " + spotId);
        }

        // Mark spot as occupied (reserved)
        spot.setOccupied(true);
        parkingRepository.save(spot);

        // Create reservation
        Reservation reservation = new Reservation(
            null,
            spotId,
            userId,
            LocalDateTime.now().plusHours(hours)
        );
        parkingRepository.saveReservation(reservation);

        // Update LED display
        int availableCount = (int) parkingRepository.findAllSpots().stream()
                .filter(ParkingSpot::isAvailable).count();
        ledDisplay.updateDisplay(availableCount);

        return reservation;
    }

    @Override
    public void cancelReservation(Long resId) {
        // Find the reservation
        Optional<Reservation> reservationOpt = parkingRepository.findReservationById(resId);
        
        if (reservationOpt.isEmpty()) {
            throw new RuntimeException("Reservation not found: " + resId);
        }

        Reservation reservation = reservationOpt.get();

        // Release the spot
        parkingRepository.updateSpotStatus(reservation.getSpotId(), true);

        // Delete the reservation
        parkingRepository.deleteReservation(resId);

        // Update LED display
        int availableCount = (int) parkingRepository.findAllSpots().stream()
                .filter(ParkingSpot::isAvailable).count();
        ledDisplay.updateDisplay(availableCount);
    }

    @Override
    public ParkingStatus getParkingStatus() {
        List<ParkingSpot> allSpots = parkingRepository.findAllSpots();
        
        // Scan all spots with sensor
        parkingSensor.scanAllSpots();

        int total = allSpots.size();
        int occupied = (int) allSpots.stream().filter(ParkingSpot::isOccupied).count();
        int available = total - occupied;
        int reservations = parkingRepository.countReservations();

        return new ParkingStatus(total, occupied, available, reservations);
    }

    @Override
    public List<ParkingSpot> getAvailableSpots() {
        return parkingRepository.findAllSpots().stream()
                .filter(ParkingSpot::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Ticket> findTicketById(Long id) {
        return parkingRepository.findTicketById(id);
    }
}
