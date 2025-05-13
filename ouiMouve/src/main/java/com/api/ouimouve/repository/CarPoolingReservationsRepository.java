package com.api.ouimouve.repository;

import com.api.ouimouve.bo.CarPoolingReservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarPoolingReservationsRepository extends JpaRepository<CarPoolingReservations, Long> {
//    List<CarPoolingReservations> findByUser(Long userId);
}
