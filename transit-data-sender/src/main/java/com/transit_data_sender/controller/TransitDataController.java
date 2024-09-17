package com.transit_data_sender.controller;

import com.transit_data_sender.service.TransitDataProducer;
import com.transit_data_sender.utility.States;
import com.transit_data_sender.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/data-sender")
public class TransitDataController {

    @Autowired
    private TransitDataProducer transitDataProducer;

    private String message;
    private final Random random = new Random();

    @Value("${data.source.rest}")
    private boolean restDataSource;

    @PostMapping("/vehicle-data")
    public String sendVehicleData() {
        if(restDataSource) {
            String vehicleId = Utils.generateVehicleId();
            String moduleId =  Utils.randomUUID();
            String state = String.valueOf(States.KARNATAKA);
            double latitude = random.nextDouble() * 180 - 90;
            double longitude = random.nextDouble() * 360 - 180;
            long timestamp = System.currentTimeMillis();

            message = "vehicleId: " + vehicleId + System.lineSeparator() +
                    "moduleId: " + moduleId + System.lineSeparator() +
                    "state: " + state + System.lineSeparator() +
                    "lat: " + latitude + System.lineSeparator() +
                    "lon: " + longitude + System.lineSeparator() +
                    "timestamp: " + timestamp;

            transitDataProducer.sendVehicleData(vehicleId, moduleId, state, latitude, longitude, timestamp);
        }
        return message;
    }
}
