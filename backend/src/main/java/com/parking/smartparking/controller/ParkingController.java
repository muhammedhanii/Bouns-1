package com.parking.smartparking.controller;

import com.parking.smartparking.command.*;
import com.parking.smartparking.model.*;
import com.parking.smartparking.service.IParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Parking operations.
 * Uses Command Pattern for all operations.
 */
@RestController
@RequestMapping("/api/parking")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingController {
    private final CommandInvoker invoker;
    private final IParkingService parkingService;

    public ParkingController(CommandInvoker invoker, IParkingService parkingService) {
        this.invoker = invoker;
        this.parkingService = parkingService;
    }

    @PostMapping("/park")
    public ResponseEntity<Ticket> parkCar(@RequestBody ParkCarRequest request) {
        Command command = new ParkCarCommand(parkingService, request.getPlateNumber());
        invoker.addCommandToQueue(command);
        Ticket ticket = (Ticket) invoker.executeNext();
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/exit")
    public ResponseEntity<Receipt> exitCar(@RequestBody ExitCarRequest request) {
        Command command = new ExitCarCommand(parkingService, request.getTicketId());
        invoker.addCommandToQueue(command);
        Receipt receipt = (Receipt) invoker.executeNext();
        return ResponseEntity.ok(receipt);
    }

    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserveSpot(@RequestBody ReserveSpotRequest request) {
        Command command = new ReserveSpotCommand(
            parkingService, 
            request.getSpotId(), 
            request.getUserId(), 
            request.getHours()
        );
        invoker.addCommandToQueue(command);
        Reservation reservation = (Reservation) invoker.executeNext();
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable Long reservationId) {
        Command command = new CancelReservationCommand(parkingService, reservationId);
        invoker.addCommandToQueue(command);
        Boolean result = (Boolean) invoker.executeNext();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status")
    public ResponseEntity<ParkingStatus> getParkingStatus() {
        Command command = new GetParkingStatusCommand(parkingService);
        invoker.addCommandToQueue(command);
        ParkingStatus status = (ParkingStatus) invoker.executeNext();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/spots/available")
    public ResponseEntity<List<ParkingSpot>> getAvailableSpots() {
        List<ParkingSpot> spots = parkingService.getAvailableSpots();
        return ResponseEntity.ok(spots);
    }

    // Request DTOs
    public static class ParkCarRequest {
        private String plateNumber;

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }
    }

    public static class ExitCarRequest {
        private Long ticketId;

        public Long getTicketId() {
            return ticketId;
        }

        public void setTicketId(Long ticketId) {
            this.ticketId = ticketId;
        }
    }

    public static class ReserveSpotRequest {
        private Long spotId;
        private String userId;
        private int hours;

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

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }
    }
}
