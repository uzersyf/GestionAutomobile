package com.renault.gestionAuto.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VehicleEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(VehicleEventConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic.vehicle-created}", groupId = "gestion-auto-consumers")
    public void onVehicleCreated(String payload) {
        // consume as raw JSON string and log/audit
        log.info("Consumed vehicle-created event: {}", payload);
    }
}
