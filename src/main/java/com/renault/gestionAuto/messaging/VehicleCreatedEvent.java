package com.renault.gestionAuto.messaging;

import com.renault.gestionAuto.domain.enums.FuelType;
import com.renault.gestionAuto.domain.enums.VehicleType;

public class VehicleCreatedEvent {
    private Long id;
    private Long garageId;
    private String brand;
    private String model;
    private int year;
    private FuelType fuelType;
    private VehicleType vehicleType;
    private long timestamp;

    public VehicleCreatedEvent() {}

    public VehicleCreatedEvent(Long id, Long garageId, String brand, String model, int year, FuelType fuelType, VehicleType vehicleType, long timestamp) {
        this.id = id;
        this.garageId = garageId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getGarageId() { return garageId; }
    public void setGarageId(Long garageId) { this.garageId = garageId; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public FuelType getFuelType() { return fuelType; }
    public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
