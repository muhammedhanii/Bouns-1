package com.parking.smartparking.model;

/**
 * Model class representing a Car in the parking system.
 */
public class Car {
    private Long carId;
    private String plateNumber;
    private String ownerName;

    public Car() {}

    public Car(Long carId, String plateNumber, String ownerName) {
        this.carId = carId;
        this.plateNumber = plateNumber;
        this.ownerName = ownerName;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCarDetails() {
        return String.format("Car[id=%d, plate=%s, owner=%s]", carId, plateNumber, ownerName);
    }
}
