package com.renault.gestionAuto.repository;

import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.entity.Vehicle;
import com.renault.gestionAuto.domain.enums.FuelType;
import com.renault.gestionAuto.domain.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VehicleRepositoryDataJpaTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Test
    void persistAndLoadVehicle_withEnums_andManufactureYearColumn_ok() {
        Garage g = new Garage();
        g.setName("Garage Test");
        g.setAddress("1 rue Test");
        g.setCity("Paris");
        g.setTelephone("+33123456789");
        g.setEmail("test@garage.fr");
        Garage savedGarage = garageRepository.save(g);

        Vehicle v = new Vehicle();
        v.setBrand("Renault");
        v.setModel("Clio");
        v.setYear(2023);
        v.setFuelType(FuelType.ESSENCE);
        v.setVehicleType(VehicleType.CITADINE);
        v.setGarage(savedGarage);
        Vehicle saved = vehicleRepository.save(v);

        assertNotNull(saved.getId());
        Vehicle reloaded = vehicleRepository.findById(saved.getId()).orElseThrow();
        assertEquals(FuelType.ESSENCE, reloaded.getFuelType());
        assertEquals(VehicleType.CITADINE, reloaded.getVehicleType());
        assertEquals(2023, reloaded.getYear());
        assertNotNull(reloaded.getGarage());
    }
}
