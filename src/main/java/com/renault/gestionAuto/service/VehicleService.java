package com.renault.gestionAuto.service;

import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.entity.Vehicle;
import com.renault.gestionAuto.exception.BusinessException;
import com.renault.gestionAuto.exception.NotFoundException;
import com.renault.gestionAuto.messaging.VehicleCreatedEvent;
import com.renault.gestionAuto.messaging.VehicleEventPublisher;
import com.renault.gestionAuto.repository.GarageRepository;
import com.renault.gestionAuto.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class VehicleService {
    private static final int MAX_VEHICLES_PER_GARAGE = 50;

    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;
    private final VehicleEventPublisher vehicleEventPublisher;

    public VehicleService(VehicleRepository vehicleRepository,
                          GarageRepository garageRepository,
                          VehicleEventPublisher vehicleEventPublisher) {
        this.vehicleRepository = vehicleRepository;
        this.garageRepository = garageRepository;
        this.vehicleEventPublisher = vehicleEventPublisher;
    }

    @Transactional
    public Vehicle create(Long garageId, Vehicle vehicle) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new NotFoundException("Garage not found: " + garageId));
        long count = vehicleRepository.countByGarage_Id(garageId);
        if (count >= MAX_VEHICLES_PER_GARAGE) {
            throw new BusinessException("Garage capacity exceeded (max 50 vehicles)");
        }
        vehicle.setGarage(garage);
        Vehicle saved = vehicleRepository.save(vehicle);
        VehicleCreatedEvent event = new VehicleCreatedEvent(
                saved.getId(), garage.getId(), saved.getBrand(), saved.getModel(), saved.getYear(),
                saved.getFuelType(), saved.getVehicleType(), Instant.now().toEpochMilli()
        );
        vehicleEventPublisher.publishVehicleCreated(event);
        return saved;
    }

    @Transactional
    public Vehicle update(Long id, Vehicle v) {
        Vehicle existing = getById(id);
        existing.setBrand(v.getBrand());
        existing.setModel(v.getModel());
        existing.setYear(v.getYear());
        existing.setFuelType(v.getFuelType());
        existing.setVehicleType(v.getVehicleType());
        return vehicleRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Vehicle v = getById(id);
        vehicleRepository.delete(v);
    }

    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle not found: " + id));
    }

    public Page<Vehicle> listByGarage(Long garageId, Pageable pageable) {
        // Ensure garage exists
        garageRepository.findById(garageId).orElseThrow(() -> new NotFoundException("Garage not found: " + garageId));
        return vehicleRepository.findAllByGarage_Id(garageId, pageable);
    }

    public List<Vehicle> listByModel(String model) {
        return vehicleRepository.findAllByModelIgnoreCase(model);
    }
}
