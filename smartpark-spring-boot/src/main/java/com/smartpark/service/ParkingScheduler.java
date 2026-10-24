package com.smartpark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParkingScheduler {
    private static final Logger log = LoggerFactory.getLogger(ParkingScheduler.class);
    private final ParkingService parkingService;

    public ParkingScheduler(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Scheduled(fixedDelayString = "${smartpark.scheduler.fixed-delay-ms:30000}")
    public void removeOverdueVehicles() {
        int removed = parkingService.autoRemoveOverdueVehicles();
        if (removed > 0) {
            log.info("Automatically removed {} overdue parking session(s)", removed);
        }
    }
}
