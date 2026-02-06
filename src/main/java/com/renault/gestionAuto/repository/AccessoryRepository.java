package com.renault.gestionAuto.repository;

import com.renault.gestionAuto.domain.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    List<Accessory> findAllByVehicle_Id(Long vehicleId);
}
