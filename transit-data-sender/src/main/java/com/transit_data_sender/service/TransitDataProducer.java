package com.transit_data_sender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class TransitDataProducer {

    private static final Logger log = LoggerFactory.getLogger(TransitDataProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "vehicle-location";

    public void sendVehicleData(String vehicleId, String moduleId, String state, double latitude, double longitude, long timestamp) {
        Map<String, Object> vehicleData = new HashMap<>();
        vehicleData.put("vehicleId", vehicleId);
        vehicleData.put("moduleId", moduleId);
        vehicleData.put("State", state);
        vehicleData.put("latitude", latitude);
        vehicleData.put("longitude", longitude);
        vehicleData.put("timestamp", timestamp);

        try {
            String message = objectMapper.writeValueAsString(vehicleData);
            kafkaTemplate.send(TOPIC, message);
            log.info("Sent data: " + message);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
    }
}
