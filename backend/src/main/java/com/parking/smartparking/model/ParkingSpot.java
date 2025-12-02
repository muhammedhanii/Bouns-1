package com.parking.smartparking.model;

/**
 * Model class representing a Parking Spot in the parking system.
 */
public class ParkingSpot {
    private Long spotId;
    private String spotNumber;
    private boolean isOccupied;
    private Car parkedCar;

    public ParkingSpot() {}

    public ParkingSpot(Long spotId, String spotNumber) {
        this.spotId = spotId;
        this.spotNumber = spotNumber;
        this.isOccupied = false;
        this.parkedCar = null;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Car getParkedCar() {
        return parkedCar;
    }

    public void setParkedCar(Car parkedCar) {
        this.parkedCar = parkedCar;
    }

    public void occupy(Car car) {
        this.parkedCar = car;
        this.isOccupied = true;
    }

    public void release() {
        this.parkedCar = null;
        this.isOccupied = false;
    }

    public boolean isAvailable() {
        return !isOccupied;
    }

    public String getDetails() {
        return String.format("ParkingSpot[id=%d, number=%s, occupied=%b]", spotId, spotNumber, isOccupied);
    }
}
