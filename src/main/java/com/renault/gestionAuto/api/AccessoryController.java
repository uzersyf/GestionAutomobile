package com.renault.gestionAuto.api;

import com.renault.gestionAuto.api.dto.AccessoryDto;
import com.renault.gestionAuto.api.mapper.AccessoryMapper;
import com.renault.gestionAuto.domain.entity.Accessory;
import com.renault.gestionAuto.service.AccessoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Accessoires", description = "Gérer les accessoires d’un véhicule (ajout, liste, mise à jour, suppression)")
public class AccessoryController {
    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @PostMapping("/vehicles/{vehicleId}/accessories")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter un accessoire à un véhicule", description = "Crée un accessoire pour le véhicule indiqué (prix > 0, type requis)")
    public AccessoryDto add(@PathVariable Long vehicleId, @Valid @RequestBody AccessoryDto accessoryDto) {
        Accessory saved = accessoryService.addToVehicle(vehicleId, AccessoryMapper.toEntity(accessoryDto, null));
        return AccessoryMapper.toDto(saved);
    }

    @GetMapping("/vehicles/{vehicleId}/accessories")
    @Operation(summary = "Lister les accessoires d’un véhicule", description = "Retourne la liste des accessoires associés au véhicule")
    public List<AccessoryDto> list(@PathVariable Long vehicleId) {
        return accessoryService.listByVehicle(vehicleId).stream().map(AccessoryMapper::toDto).toList();
    }

    @PutMapping("/accessories/{id}")
    @Operation(summary = "Mettre à jour un accessoire", description = "Modifie les attributs d’un accessoire (nom, description, prix, type)")
    public AccessoryDto update(@PathVariable Long id, @Valid @RequestBody AccessoryDto accessoryDto) {
        Accessory updated = accessoryService.update(id, AccessoryMapper.toEntity(accessoryDto, null));
        return AccessoryMapper.toDto(updated);
    }

    @DeleteMapping("/accessories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un accessoire", description = "Supprime l’accessoire correspondant à l’identifiant fourni")
    public void delete(@PathVariable Long id) {
        accessoryService.delete(id);
    }
}
