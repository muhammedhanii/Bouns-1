package com.parking.smartparking.hardware;

import org.springframework.stereotype.Component;

/**
 * Hardware class representing an LED Display.
 */
@Component
public class LEDDisplay implements HardwareReceiver {
    private int displayId;
    private String message;

    public LEDDisplay() {
        this.displayId = 1;
        this.message = "";
    }

    public LEDDisplay(int displayId) {
        this.displayId = displayId;
        this.message = "";
    }

    @Override
    public void execute() {
        showMessage("Welcome to Smart Parking");
    }

    public void showAvailableCount(int count) {
        this.message = "Available Spots: " + count;
        System.out.println("Display " + displayId + ": " + message);
    }

    public void showMessage(String msg) {
        this.message = msg;
        System.out.println("Display " + displayId + ": " + message);
    }

    public void clear() {
        this.message = "";
        System.out.println("Display " + displayId + " cleared");
    }

    public void updateDisplay(int availableCount) {
        showAvailableCount(availableCount);
    }

    public int getDisplayId() {
        return displayId;
    }

    public void setDisplayId(int displayId) {
        this.displayId = displayId;
    }

    public String getMessage() {
        return message;
    }
}
