package com.renault.gestionAuto.domain.entity;

import com.renault.gestionAuto.domain.enums.FuelType;
import com.renault.gestionAuto.domain.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @Min(1900)
    @Max(2100)
    private int year;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Garage garage;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accessory> accessories = new ArrayList<>();

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
    public Garage getGarage() { return garage; }
    public void setGarage(Garage garage) { this.garage = garage; }
    public List<Accessory> getAccessories() { return accessories; }
    public void setAccessories(List<Accessory> accessories) { this.accessories = accessories; }
}
