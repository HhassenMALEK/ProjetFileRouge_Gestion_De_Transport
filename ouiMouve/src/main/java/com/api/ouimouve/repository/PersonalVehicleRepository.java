package com.api.ouimouve.repository;

import com.api.ouimouve.bo.PersonalVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Reparation entities.
 * This interface extends JpaRepository to provide CRUD operations
 */
@Repository
public interface PersonalVehicleRepository extends JpaRepository<PersonalVehicle, Long> {
    List<PersonalVehicle> findByUserId(Long userId);
}
