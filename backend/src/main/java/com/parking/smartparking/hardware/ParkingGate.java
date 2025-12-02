package com.parking.smartparking.hardware;

import org.springframework.stereotype.Component;

/**
 * Hardware class representing a Parking Gate.
 */
@Component
public class ParkingGate implements HardwareReceiver {
    private int gateId;
    private boolean isOpen;

    public ParkingGate() {
        this.gateId = 1;
        this.isOpen = false;
    }

    public ParkingGate(int gateId) {
        this.gateId = gateId;
        this.isOpen = false;
    }

    @Override
    public void execute() {
        openGate();
    }

    public void openGate() {
        this.isOpen = true;
        System.out.println("Gate " + gateId + " opened");
    }

    public void closeGate() {
        this.isOpen = false;
        System.out.println("Gate " + gateId + " closed");
    }

    public String getStatus() {
        return isOpen ? "OPEN" : "CLOSED";
    }

    public int getGateId() {
        return gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
