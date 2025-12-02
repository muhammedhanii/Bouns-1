package com.parking.smartparking.command;

import com.parking.smartparking.model.Ticket;
import com.parking.smartparking.service.IParkingService;

/**
 * Concrete Command for parking a car.
 */
public class ParkCarCommand implements Command {
    private final IParkingService parkingService;
    private final String plateNumber;
    private Long spotId;
    private Ticket createdTicket;

    public ParkCarCommand(IParkingService parkingService, String plateNumber) {
        this.parkingService = parkingService;
        this.plateNumber = plateNumber;
    }

    @Override
    public Ticket execute() {
        createdTicket = parkingService.parkCar(plateNumber);
        spotId = createdTicket.getSpotId();
        return createdTicket;
    }

    @Override
    public void undo() {
        if (createdTicket != null) {
            parkingService.exitCar(createdTicket.getTicketId());
        }
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Long getSpotId() {
        return spotId;
    }
}
