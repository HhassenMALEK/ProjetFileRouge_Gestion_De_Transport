package com.api.ouimouve.repository;

import com.api.ouimouve.bo.VehicleReservation;
import com.api.ouimouve.enumeration.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for managing Reparation entities.
 * This interface extends JpaRepository to provide CRUD operations
 */
@Repository
public interface VehicleReservationRepository extends JpaRepository<VehicleReservation, Long> {

    List<VehicleReservation> findByServiceVehicleId(Long vehicleID);
    List<VehicleReservation> findByUserId(Long userID);

    @Query("SELECT vr FROM VehicleReservation vr WHERE vr.user.id = :userId " +
            "AND (:start IS NULL OR vr.start >= :start) " +
            "AND (:status IS NULL OR vr.status = :status)")
    List<VehicleReservation> findByUserWithFilters(
            @Param("userId") Long userId,
            @Param("start") Date start,
            @Param("status") VehicleStatus status);

    /**
     * Find all reservations that overlap with the given period for a specific vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @param start the start date of the period
     * @param end the end date of the period
     * @return a list of overlapping reservations
     */
    @Query("SELECT vr FROM VehicleReservation vr WHERE vr.serviceVehicle.id = :vehicleId " +
           "AND vr.start < :end AND vr.end > :start")
    List<VehicleReservation> findOverlappingReservations(
            @Param("vehicleId") Long vehicleId,
            @Param("start") Date start,
            @Param("end") Date end);
}
