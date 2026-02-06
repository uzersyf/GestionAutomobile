package com.renault.gestionAuto.api.dto;

import com.renault.gestionAuto.domain.enums.FuelType;
import com.renault.gestionAuto.domain.enums.VehicleType;
import jakarta.validation.constraints.*;

public class VehicleDto {
    private Long id;
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @Min(1900)
    @Max(2100)
    private int year;
    @NotNull
    private FuelType fuelType;
    @NotNull
    private VehicleType vehicleType;
    private Long garageId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Long getGarageId() { return garageId; }
    public void setGarageId(Long garageId) { this.garageId = garageId; }
}
