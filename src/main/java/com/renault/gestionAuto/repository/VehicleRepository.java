package com.renault.gestionAuto.repository;

import com.renault.gestionAuto.domain.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Page<Vehicle> findAllByGarage_Id(Long garageId, Pageable pageable);
    List<Vehicle> findAllByModelIgnoreCase(String model);
    long countByGarage_Id(Long garageId);
}
