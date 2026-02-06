package com.renault.gestionAuto.api;

import com.renault.gestionAuto.domain.entity.Vehicle;
import com.renault.gestionAuto.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/garages/{garageId}/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@PathVariable Long garageId, @Valid @RequestBody Vehicle vehicle) {
        return vehicleService.create(garageId, vehicle);
    }

    @GetMapping("/garages/{garageId}/vehicles")
    public Page<Vehicle> listByGarage(@PathVariable Long garageId, @PageableDefault(size = 20) Pageable pageable) {
        return vehicleService.listByGarage(garageId, pageable);
    }

    @GetMapping("/vehicles/{id}")
    public Vehicle get(@PathVariable Long id) {
        return vehicleService.getById(id);
    }

    @PutMapping("/vehicles/{id}")
    public Vehicle update(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        return vehicleService.update(id, vehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vehicleService.delete(id);
    }

    @GetMapping("/vehicles/search/by-model")
    public List<Vehicle> listByModel(@RequestParam("model") String model) {
        return vehicleService.listByModel(model);
    }
}
