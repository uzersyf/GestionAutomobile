package com.renault.gestionAuto.api.mapper;

import com.renault.gestionAuto.api.dto.VehicleDto;
import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.entity.Vehicle;

public final class VehicleMapper {
    private VehicleMapper() {}

    public static VehicleDto toDto(Vehicle v) {
        VehicleDto dto = new VehicleDto();
        dto.setId(v.getId());
        dto.setBrand(v.getBrand());
        dto.setModel(v.getModel());
        dto.setYear(v.getYear());
        dto.setFuelType(v.getFuelType());
        dto.setVehicleType(v.getVehicleType());
        dto.setGarageId(v.getGarage() != null ? v.getGarage().getId() : null);
        return dto;
    }

    public static Vehicle toEntity(VehicleDto dto, Garage garage) {
        Vehicle v = new Vehicle();
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setYear(dto.getYear());
        v.setFuelType(dto.getFuelType());
        v.setVehicleType(dto.getVehicleType());
        v.setGarage(garage);
        return v;
    }
}
