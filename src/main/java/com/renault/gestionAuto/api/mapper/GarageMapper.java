package com.renault.gestionAuto.api.mapper;

import com.renault.gestionAuto.api.dto.GarageDto;
import com.renault.gestionAuto.domain.entity.Garage;

public final class GarageMapper {
    private GarageMapper() {}

    public static GarageDto toDto(Garage g) {
        GarageDto dto = new GarageDto();
        dto.setId(g.getId());
        dto.setName(g.getName());
        dto.setAddress(g.getAddress());
        dto.setCity(g.getCity());
        dto.setTelephone(g.getTelephone());
        dto.setEmail(g.getEmail());
        return dto;
    }

    public static Garage toEntity(GarageDto dto) {
        Garage g = new Garage();
        g.setName(dto.getName());
        g.setAddress(dto.getAddress());
        g.setCity(dto.getCity());
        g.setTelephone(dto.getTelephone());
        g.setEmail(dto.getEmail());
        return g;
    }
}
