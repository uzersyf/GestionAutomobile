package com.renault.gestionAuto.service;

import com.renault.gestionAuto.domain.entity.Garage;
import com.renault.gestionAuto.domain.entity.Vehicle;
import com.renault.gestionAuto.domain.enums.FuelType;
import com.renault.gestionAuto.domain.enums.VehicleType;
import com.renault.gestionAuto.exception.BusinessException;
import com.renault.gestionAuto.exception.NotFoundException;
import com.renault.gestionAuto.messaging.VehicleEventPublisher;
import com.renault.gestionAuto.repository.GarageRepository;
import com.renault.gestionAuto.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    private VehicleRepository vehicleRepository;
    private GarageRepository garageRepository;
    private VehicleEventPublisher publisher;
    private VehicleService vehicleService;

    @BeforeEach
    void setup() {
        vehicleRepository = mock(VehicleRepository.class);
        garageRepository = mock(GarageRepository.class);
        publisher = mock(VehicleEventPublisher.class);
        vehicleService = new VehicleService(vehicleRepository, garageRepository, publisher);
    }

    @Test
    void createVehicle_success_whenUnderCapacity_publishesEvent() {
        Long garageId = 1L;
        Garage g = new Garage();
        g.setId(garageId);
        when(garageRepository.findById(garageId)).thenReturn(Optional.of(g));
        when(vehicleRepository.countByGarage_Id(garageId)).thenReturn(10L);

        Vehicle v = new Vehicle();
        v.setBrand("Renault");
        v.setModel("Clio");
        v.setYear(2024);
        v.setFuelType(FuelType.ESSENCE);
        v.setVehicleType(VehicleType.CITADINE);

        Vehicle persisted = new Vehicle();
        persisted.setId(100L);
        persisted.setBrand(v.getBrand());
        persisted.setModel(v.getModel());
        persisted.setYear(v.getYear());
        persisted.setFuelType(v.getFuelType());
        persisted.setVehicleType(v.getVehicleType());
        persisted.setGarage(g);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(persisted);

        Vehicle result = vehicleService.create(garageId, v);

        assertNotNull(result.getId());
        verify(publisher, times(1)).publishVehicleCreated(any());
    }

    @Test
    void createVehicle_fails_whenCapacityExceeded() {
        Long garageId = 1L;
        when(garageRepository.findById(garageId)).thenReturn(Optional.of(new Garage()));
        when(vehicleRepository.countByGarage_Id(garageId)).thenReturn(50L);

        Vehicle v = new Vehicle();
        v.setBrand("Renault");
        v.setModel("Megane");
        v.setYear(2022);
        v.setFuelType(FuelType.DIESEL);
        v.setVehicleType(VehicleType.BERLINE);

        assertThrows(BusinessException.class, () -> vehicleService.create(garageId, v));
        verifyNoInteractions(publisher);
    }

    @Test
    void createVehicle_fails_whenGarageNotFound() {
        Long garageId = 42L;
        when(garageRepository.findById(garageId)).thenReturn(Optional.empty());

        Vehicle v = new Vehicle();
        v.setBrand("Renault");
        v.setModel("Zoe");
        v.setYear(2020);
        v.setFuelType(FuelType.ELECTRIQUE);
        v.setVehicleType(VehicleType.CITADINE);

        assertThrows(NotFoundException.class, () -> vehicleService.create(garageId, v));
        verifyNoInteractions(publisher);
    }
}
