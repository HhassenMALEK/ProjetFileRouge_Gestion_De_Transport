package com.api.ouimouve.repository;

import com.api.ouimouve.bo.CarPoolingReservations;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarPoolingReservationsRepository extends JpaRepository<CarPoolingReservations, Long> {
    List<CarPoolingReservations> findByUserIdAndDateAfter(Long userId, Date date);
    int countByCarPoolingIdAndStatus(Long carPoolingId, CarPoolingReservationStatus status);
}
