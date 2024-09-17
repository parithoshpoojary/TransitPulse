package com.transit_data_sender.scheduler;

import com.transit_data_sender.service.TransitDataProducer;
import com.transit_data_sender.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class TransitDataScheduler {

    @Autowired
    private TransitDataProducer transitDataProducer;

    private final Random random = new Random();
    private final String KARNATAKA = "Karnataka";

    @Value("${data.source.scheduler}")
    private boolean schedulerDataSource;

    @Scheduled(fixedRate = 50000)
    public void sendVehicleData() {
        if(schedulerDataSource) {
            String vehicleId = Utils.generateVehicleId();
            String moduleId = "module-" + UUID.randomUUID().toString();
            String state = KARNATAKA;
            double latitude = random.nextDouble() * 180 - 90;
            double longitude = random.nextDouble() * 360 - 180;
            long timestamp = System.currentTimeMillis();

            transitDataProducer.sendVehicleData(vehicleId, moduleId, state, latitude, longitude, timestamp);
        }
    }
}
