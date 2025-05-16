package com.api.ouimouve.repository;

import com.api.ouimouve.bo.Reparation;
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
public interface ReparationRepository extends JpaRepository<Reparation, Long> {
    // Custom query methods can be defined here if needed
    // For example, find by vehicle ID or date range
    List<Reparation> findByServiceVehicleId(Long serviceVehicle);


    /**
     * Find all repairs that overlap with the given period for a specific vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @param start the start date of the period
     * @param end the end date of the period
     * @return a list of overlapping repairs
     */
    @Query("SELECT r FROM Reparation r WHERE r.serviceVehicle.id = :vehicleId " +
           "AND r.start < :end AND r.end > :start")
    List<Reparation> findOverlappingReparations(
            @Param("vehicleId") Long vehicleId,
            @Param("start") Date start,
            @Param("end") Date end);
}
