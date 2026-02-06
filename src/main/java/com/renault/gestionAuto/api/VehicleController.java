package com.renault.gestionAuto.api;

import com.renault.gestionAuto.api.dto.VehicleDto;
import com.renault.gestionAuto.api.mapper.VehicleMapper;
import com.renault.gestionAuto.domain.entity.Vehicle;
import com.renault.gestionAuto.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Véhicules", description = "Gérer les véhicules (ajout, liste par garage, lecture, mise à jour, suppression, recherche par modèle)")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/garages/{garageId}/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter un véhicule à un garage", description = "Crée un véhicule dans le garage donné. En cas de dépassement du quota (50), renvoie 409.")
    public VehicleDto create(@PathVariable Long garageId, @Valid @RequestBody VehicleDto dto) {
        Vehicle created = vehicleService.create(garageId, VehicleMapper.toEntity(dto, null));
        return VehicleMapper.toDto(created);
    }

    @GetMapping("/garages/{garageId}/vehicles")
    @Operation(summary = "Lister les véhicules d’un garage", description = "Retourne la liste paginée des véhicules appartenant au garage fourni")
    public Page<VehicleDto> listByGarage(@PathVariable Long garageId, @PageableDefault(size = 20) Pageable pageable) {
        return vehicleService.listByGarage(garageId, pageable).map(VehicleMapper::toDto);
    }

    @GetMapping("/vehicles/{id}")
    @Operation(summary = "Récupérer un véhicule", description = "Retourne les informations d’un véhicule par son identifiant")
    public VehicleDto get(@PathVariable Long id) {
        return VehicleMapper.toDto(vehicleService.getById(id));
    }

    @PutMapping("/vehicles/{id}")
    @Operation(summary = "Mettre à jour un véhicule", description = "Modifie les champs d’un véhicule (marque, modèle, année, carburant, type)")
    public VehicleDto update(@PathVariable Long id, @Valid @RequestBody VehicleDto dto) {
        Vehicle updated = vehicleService.update(id, VehicleMapper.toEntity(dto, null));
        return VehicleMapper.toDto(updated);
    }

    @DeleteMapping("/vehicles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un véhicule", description = "Supprime le véhicule et, en cascade, ses accessoires")
    public void delete(@PathVariable Long id) {
        vehicleService.delete(id);
    }

    @GetMapping("/vehicles/search/by-model")
    @Operation(summary = "Lister tous les véhicules d’un modèle", description = "Recherche multi-garages par nom exact du modèle (insensible à la casse)")
    public List<VehicleDto> listByModel(@RequestParam("model") String model) {
        return vehicleService.listByModel(model).stream().map(VehicleMapper::toDto).collect(Collectors.toList());
    }
}
