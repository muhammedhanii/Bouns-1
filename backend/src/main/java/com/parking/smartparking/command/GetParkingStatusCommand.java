package com.parking.smartparking.command;

import com.parking.smartparking.model.ParkingStatus;
import com.parking.smartparking.service.IParkingService;

/**
 * Concrete Command for getting parking status.
 */
public class GetParkingStatusCommand implements Command {
    private final IParkingService parkingService;

    public GetParkingStatusCommand(IParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Override
    public ParkingStatus execute() {
        return parkingService.getParkingStatus();
    }

    @Override
    public void undo() {
        // Read-only operation, nothing to undo
    }
}
