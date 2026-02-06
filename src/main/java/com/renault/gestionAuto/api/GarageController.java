package com.renault.gestionAuto.api;

import com.renault.gestionAuto.api.dto.GarageDto;
import com.renault.gestionAuto.api.mapper.GarageMapper;
import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.enums.VehicleType;
import com.renault.gestionAuto.service.GarageService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;

@RestController
@RequestMapping("/api/v1/garages")
@Tag(name = "Garages", description = "Gérer les garages du réseau (création, lecture, mise à jour, suppression, recherche)")
public class GarageController {
    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un garage", description = "Ajoute un nouveau garage avec ses informations de base (nom, adresse, ville, téléphone, email)")
    public GarageDto create(@Valid @RequestBody GarageDto garageDto) {
        Garage saved = garageService.create(GarageMapper.toEntity(garageDto));
        return GarageMapper.toDto(saved);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un garage", description = "Retourne les informations d’un garage en fonction de son identifiant")
    public GarageDto get(@PathVariable Long id) {
        return GarageMapper.toDto(garageService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un garage", description = "Modifie les informations d’un garage existant")
    public GarageDto update(@PathVariable Long id, @Valid @RequestBody GarageDto garageDto) {
        Garage updated = garageService.update(id, GarageMapper.toEntity(garageDto));
        return GarageMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un garage", description = "Supprime un garage et, en cascade, ses véhicules et leurs accessoires")
    public void delete(@PathVariable Long id) {
        garageService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Lister les garages", description = "Retourne la liste paginée des garages, avec possibilités de tri")
    public Page<GarageDto> list(@ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        return garageService.list(pageable).map(GarageMapper::toDto);
    }

    @GetMapping("/search/by-vehicle-type")
    @Operation(summary = "Rechercher par type de véhicule", description = "Liste les garages qui possèdent au moins un véhicule du type spécifié (ex: SUV)")
    public Page<GarageDto> findByVehicleType(@RequestParam("type") VehicleType type,
                                          @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        return garageService.findByVehicleType(type, pageable).map(GarageMapper::toDto);
    }

    @GetMapping("/search/by-accessory")
    @Operation(summary = "Rechercher par accessoire disponible", description = "Liste les garages qui ont au moins un véhicule avec un accessoire dont le nom contient la valeur fournie (insensible à la casse)")
    public Page<GarageDto> findByAccessoryName(@RequestParam("name") String accessoryName,
                                            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        return garageService.findByAccessoryName(accessoryName, pageable).map(GarageMapper::toDto);
    }
}
