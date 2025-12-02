package com.parking.smartparking.hardware;

import org.springframework.stereotype.Component;

/**
 * Hardware class representing a Parking Sensor.
 */
@Component
public class ParkingSensor implements HardwareReceiver {
    private int sensorId;
    private Long spotId;
    private boolean carDetected;

    public ParkingSensor() {
        this.sensorId = 1;
        this.carDetected = false;
    }

    public ParkingSensor(int sensorId, Long spotId) {
        this.sensorId = sensorId;
        this.spotId = spotId;
        this.carDetected = false;
    }

    @Override
    public void execute() {
        detectCar();
    }

    public boolean detectCar() {
        System.out.println("Sensor " + sensorId + " detecting car on spot " + spotId);
        return carDetected;
    }

    public void updateSpotStatus() {
        System.out.println("Sensor " + sensorId + " updating spot " + spotId + " status");
    }

    public void triggerAlert() {
        System.out.println("Sensor " + sensorId + " alert triggered!");
    }

    public void scanAllSpots() {
        System.out.println("Scanning all spots with sensor " + sensorId);
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public boolean isCarDetected() {
        return carDetected;
    }

    public void setCarDetected(boolean carDetected) {
        this.carDetected = carDetected;
    }
}
