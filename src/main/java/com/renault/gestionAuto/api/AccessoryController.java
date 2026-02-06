package com.renault.gestionAuto.api;

import com.renault.gestionAuto.domain.entity.Accessory;
import com.renault.gestionAuto.service.AccessoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccessoryController {
    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @PostMapping("/vehicles/{vehicleId}/accessories")
    @ResponseStatus(HttpStatus.CREATED)
    public Accessory add(@PathVariable Long vehicleId, @Valid @RequestBody Accessory accessory) {
        return accessoryService.addToVehicle(vehicleId, accessory);
    }

    @GetMapping("/vehicles/{vehicleId}/accessories")
    public List<Accessory> list(@PathVariable Long vehicleId) {
        return accessoryService.listByVehicle(vehicleId);
    }

    @PutMapping("/accessories/{id}")
    public Accessory update(@PathVariable Long id, @Valid @RequestBody Accessory accessory) {
        return accessoryService.update(id, accessory);
    }

    @DeleteMapping("/accessories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accessoryService.delete(id);
    }
}
