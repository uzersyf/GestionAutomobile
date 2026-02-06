package com.renault.gestionAuto.service;

import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.enums.VehicleType;
import com.renault.gestionAuto.exception.NotFoundException;
import com.renault.gestionAuto.repository.GarageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GarageService {
    private final GarageRepository garageRepository;

    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public Garage create(Garage g) { return garageRepository.save(g); }

    public Garage update(Long id, Garage g) {
        Garage existing = getById(id);
        existing.setName(g.getName());
        existing.setAddress(g.getAddress());
        existing.setCity(g.getCity());
        existing.setTelephone(g.getTelephone());
        existing.setEmail(g.getEmail());
        return garageRepository.save(existing);
    }

    public void delete(Long id) { garageRepository.delete(getById(id)); }

    public Garage getById(Long id) {
        return garageRepository.findById(id).orElseThrow(() -> new NotFoundException("Garage not found: " + id));
    }

    public Page<Garage> list(Pageable pageable) { return garageRepository.findAll(pageable); }

    public Page<Garage> findByVehicleType(VehicleType type, Pageable pageable) {
        return garageRepository.findByVehicleType(type, pageable);
    }

    public Page<Garage> findByAccessoryName(String accessoryName, Pageable pageable) {
        return garageRepository.findByAccessoryName(accessoryName, pageable);
    }
}
