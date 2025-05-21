package com.api.ouimouve.repository;

import com.api.ouimouve.bo.ServiceVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Reparation entities.
 * This interface extends JpaRepository to provide CRUD operations
 */
@Repository
public interface ServiceVehicleRepository extends JpaRepository<ServiceVehicle, Long> {
}
