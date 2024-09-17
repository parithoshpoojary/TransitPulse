package com.transit_data_sender.controller;

import com.transit_data_sender.service.TransitDataProducer;
import com.transit_data_sender.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/data-sender")
public class TransitDataController {

    @Autowired
    private TransitDataProducer transitDataProducer;
    private String message;
    private final Random random = new Random();
    private final String KARNATAKA = "Karnataka";

    @Value("${data.source.rest}")
    private boolean restDataSource;

    @PostMapping("/vehicle-data")
    public String sendVehicleData() {
        if(restDataSource) {
            String vehicleId = Utils.generateVehicleId();
            String moduleId = "module-" + UUID.randomUUID().toString();
            String state = KARNATAKA;
            double latitude = random.nextDouble() * 180 - 90;
            double longitude = random.nextDouble() * 360 - 180;
            long timestamp = System.currentTimeMillis();

            message = "vehicleId: " + vehicleId + "\n" + "moduleId: " + moduleId + "\n" + "state: "
                    + state + "\n" + "lat: " + latitude + "\n" + "lon: " + longitude + "\n" + "timestamp: " + timestamp;

            transitDataProducer.sendVehicleData(vehicleId, moduleId, state, latitude, longitude, timestamp);
        }
        return message;
    }
}
