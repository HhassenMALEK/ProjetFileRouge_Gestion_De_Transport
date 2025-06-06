package com.api.ouimouve.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ouimouve.bo.Vehicle;

/**
 * Repository interface for managing Reparation entities.
 * This interface extends JpaRepository to provide CRUD operations
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByImmatriculation(String immatriculation);

}
