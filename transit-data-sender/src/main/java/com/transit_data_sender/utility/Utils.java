package com.transit_data_sender.utility;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public static String generateVehicleId() {
        Random random = new Random();

        // Generate random 2-digit number
        String twoDigitNumber = String.format("%02d", random.nextInt(100));

        // Generate random 2-letter string using IntStream and streams to pick letters
        String randomText = IntStream.range(0, 2)
                .mapToObj(i -> String.valueOf((char) ('A' + random.nextInt(26))))
                .collect(Collectors.joining());

        // Generate random 4-digit number
        String fourDigitNumber = String.format("%04d", random.nextInt(10000));

        // Return the generated vehicle ID in the required format
        return "KA-" + twoDigitNumber + "-" + randomText + "-" + fourDigitNumber;
    }

    public static String randomUUID(){
        return UUID.randomUUID().toString();
    }

}
