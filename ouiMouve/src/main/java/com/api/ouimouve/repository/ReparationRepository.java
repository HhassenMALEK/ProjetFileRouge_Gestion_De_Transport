package com.api.ouimouve.repository;

import com.api.ouimouve.bo.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    // List<Reparation> findByStartDateBetween(Date startDate, Date endDate);
}
