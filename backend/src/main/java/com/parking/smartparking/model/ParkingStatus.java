package com.parking.smartparking.model;

/**
 * Model class representing the Parking Status in the parking system.
 */
public class ParkingStatus {
    private int totalSpots;
    private int occupiedSpots;
    private int availableSpots;
    private int totalReservations;

    public ParkingStatus() {}

    public ParkingStatus(int totalSpots, int occupiedSpots, int availableSpots, int totalReservations) {
        this.totalSpots = totalSpots;
        this.occupiedSpots = occupiedSpots;
        this.availableSpots = availableSpots;
        this.totalReservations = totalReservations;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public int getOccupiedSpots() {
        return occupiedSpots;
    }

    public void setOccupiedSpots(int occupiedSpots) {
        this.occupiedSpots = occupiedSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public int getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(int totalReservations) {
        this.totalReservations = totalReservations;
    }

    public double getOccupancyPercentage() {
        if (totalSpots == 0) return 0;
        return (double) occupiedSpots / totalSpots * 100.0;
    }

    public String getStatusMessage() {
        return String.format("Available: %d/%d, Occupancy: %.1f%%", 
            availableSpots, totalSpots, getOccupancyPercentage());
    }
}
