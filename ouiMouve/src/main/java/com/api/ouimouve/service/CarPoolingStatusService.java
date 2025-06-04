package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.repository.CarPoolingRepository;
import com.api.ouimouve.repository.CarPoolingReservationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarPoolingStatusService {

    @Autowired
    CarPoolingRepository carPoolingRepository;
    @Autowired
    CarPoolingReservationsRepository reservationRepository;

    /**
     * Batch that:
     * - Retrieves carpools whose arrival date has passed and which are neither finished nor canceled
     * - Updates their status to FINISHED
     * - Updates the status of associated reservations to FINISHED, except for those already canceled
     */
    @Transactional
    public void closeExpiredCarpoolings() {

            List<CarPooling> carPoolings = carPoolingRepository.findAllNonFinishedNonCancelled();

        LocalDateTime now = LocalDateTime.now();

        for (CarPooling cp : carPoolings) {
            if (cp.getDeparture() == null || cp.getDurationInMinutes() == null) continue;

            LocalDateTime departureTime = cp.getDeparture().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            LocalDateTime arrivalTime = departureTime.plusMinutes(cp.getDurationInMinutes());

            if (arrivalTime.isBefore(now)) {
                // Update the carpooling status to FINISHED
                cp.setStatus(CarPoolingStatus.FINISHED);
                carPoolingRepository.save(cp);

                // Update the status of associated reservations to FINISHED
                cp.getReservations().stream()
                        .filter(res -> res.getStatus() != CarPoolingReservationStatus.CANCELLED)
                        .forEach(res -> {
                            res.setStatus(CarPoolingReservationStatus.FINISHED);
                            reservationRepository.save(res);
                        });
            }
        }
    }
}

