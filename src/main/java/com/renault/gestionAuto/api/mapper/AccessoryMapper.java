package com.renault.gestionAuto.api.mapper;

import com.renault.gestionAuto.api.dto.AccessoryDto;
import com.renault.gestionAuto.domain.entity.Accessory;
import com.renault.gestionAuto.domain.entity.Vehicle;

public final class AccessoryMapper {
    private AccessoryMapper() {}

    public static AccessoryDto toDto(Accessory a) {
        AccessoryDto dto = new AccessoryDto();
        dto.setId(a.getId());
        dto.setName(a.getName());
        dto.setDescription(a.getDescription());
        dto.setPrice(a.getPrice());
        dto.setType(a.getType());
        dto.setVehicleId(a.getVehicle() != null ? a.getVehicle().getId() : null);
        return dto;
    }

    public static Accessory toEntity(AccessoryDto dto, Vehicle vehicle) {
        Accessory a = new Accessory();
        a.setName(dto.getName());
        a.setDescription(dto.getDescription());
        a.setPrice(dto.getPrice());
        a.setType(dto.getType());
        a.setVehicle(vehicle);
        return a;
    }
}
