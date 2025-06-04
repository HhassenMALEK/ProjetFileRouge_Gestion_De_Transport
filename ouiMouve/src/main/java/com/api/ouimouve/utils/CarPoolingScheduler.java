package com.api.ouimouve.utils;


import com.api.ouimouve.service.CarPoolingStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for clean the old carpoolings in database
 */
@Component
@RequiredArgsConstructor
public class CarPoolingScheduler {

    @Autowired
    CarPoolingStatusService carPoolingStatusService;
    /**
     * Scheduled method to close expired carpoolings.
     * This method is called periodically to update the status of carpoolings and their reservations.
     */
    @Scheduled(cron="*/10 * * * * ?") // Every 30 seconds
    public void closeExpiredCarpoolings() {
        carPoolingStatusService.closeExpiredCarpoolings();
    }

}
