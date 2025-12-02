package com.parking.smartparking.command;

import com.parking.smartparking.model.Receipt;
import com.parking.smartparking.service.IParkingService;

/**
 * Concrete Command for exiting a car from the parking.
 */
public class ExitCarCommand implements Command {
    private final IParkingService parkingService;
    private final Long ticketId;
    private Receipt generatedReceipt;

    public ExitCarCommand(IParkingService parkingService, Long ticketId) {
        this.parkingService = parkingService;
        this.ticketId = ticketId;
    }

    @Override
    public Receipt execute() {
        generatedReceipt = parkingService.exitCar(ticketId);
        return generatedReceipt;
    }

    @Override
    public void undo() {
        // Exit operation cannot be easily undone
        // Would need to re-create the ticket with original entry time
        System.out.println("Exit operation cannot be undone");
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Receipt getGeneratedReceipt() {
        return generatedReceipt;
    }
}
