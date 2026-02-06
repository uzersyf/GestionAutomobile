package com.renault.gestionAuto.repository;

import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.enums.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    Page<Garage> findAllByCityIgnoreCase(String city, Pageable pageable);

    @Query("select distinct g from Garage g join g.vehicles v where v.vehicleType = :vehicleType")
    Page<Garage> findByVehicleType(@Param("vehicleType") VehicleType vehicleType, Pageable pageable);

    @Query("select distinct g from Garage g join g.vehicles v join v.accessories a where lower(a.name) like lower(concat('%', :accessoryName, '%'))")
    Page<Garage> findByAccessoryName(@Param("accessoryName") String accessoryName, Pageable pageable);
}
