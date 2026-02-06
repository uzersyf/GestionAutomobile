package com.renault.gestionAuto.service;

import com.renault.gestionAuto.domain.entity.Accessory;
import com.renault.gestionAuto.domain.entity.Vehicle;
import com.renault.gestionAuto.exception.NotFoundException;
import com.renault.gestionAuto.repository.AccessoryRepository;
import com.renault.gestionAuto.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccessoryService {
    private final AccessoryRepository accessoryRepository;
    private final VehicleRepository vehicleRepository;

    public AccessoryService(AccessoryRepository accessoryRepository, VehicleRepository vehicleRepository) {
        this.accessoryRepository = accessoryRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Accessory addToVehicle(Long vehicleId, Accessory accessory) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found: " + vehicleId));
        accessory.setVehicle(vehicle);
        return accessoryRepository.save(accessory);
    }

    public List<Accessory> listByVehicle(Long vehicleId) {
        // ensure vehicle exists
        vehicleRepository.findById(vehicleId).orElseThrow(() -> new NotFoundException("Vehicle not found: " + vehicleId));
        return accessoryRepository.findAllByVehicle_Id(vehicleId);
    }

    @Transactional
    public Accessory update(Long id, Accessory a) {
        Accessory existing = accessoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Accessory not found: " + id));
        existing.setName(a.getName());
        existing.setDescription(a.getDescription());
        existing.setPrice(a.getPrice());
        existing.setType(a.getType());
        return accessoryRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) { accessoryRepository.delete(accessoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Accessory not found: " + id))); }
}
