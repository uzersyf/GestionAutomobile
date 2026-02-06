package com.renault.gestionAuto.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaVehicleEventPublisher implements VehicleEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public KafkaVehicleEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                      @Value("${app.kafka.topic.vehicle-created}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publishVehicleCreated(VehicleCreatedEvent event) {
        String key = event.getId() != null ? event.getId().toString() : null;
        kafkaTemplate.send(topic, key, event);
    }
}
