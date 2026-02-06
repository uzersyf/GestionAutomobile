package com.renault.gestionAuto.api;

import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.enums.VehicleType;
import com.renault.gestionAuto.service.GarageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/garages")
public class GarageController {
    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Garage create(@Valid @RequestBody Garage garage) {
        return garageService.create(garage);
    }

    @GetMapping("/{id}")
    public Garage get(@PathVariable Long id) {
        return garageService.getById(id);
    }

    @PutMapping("/{id}")
    public Garage update(@PathVariable Long id, @Valid @RequestBody Garage garage) {
        return garageService.update(id, garage);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        garageService.delete(id);
    }

    @GetMapping
    public Page<Garage> list(@PageableDefault(size = 20) Pageable pageable) {
        return garageService.list(pageable);
    }

    @GetMapping("/search/by-vehicle-type")
    public Page<Garage> findByVehicleType(@RequestParam("type") VehicleType type,
                                          @PageableDefault(size = 20) Pageable pageable) {
        return garageService.findByVehicleType(type, pageable);
    }

    @GetMapping("/search/by-accessory")
    public Page<Garage> findByAccessoryName(@RequestParam("name") String accessoryName,
                                            @PageableDefault(size = 20) Pageable pageable) {
        return garageService.findByAccessoryName(accessoryName, pageable);
    }
}
