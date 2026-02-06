package com.renault.gestionAuto.messaging;

public interface VehicleEventPublisher {
    void publishVehicleCreated(VehicleCreatedEvent event);
}
