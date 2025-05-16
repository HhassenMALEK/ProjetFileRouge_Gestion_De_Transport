package com.api.ouimouve.repository;

import com.api.ouimouve.bo.Vehicle;
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
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    //List<Vehicle> findByServiceVehicleId(Long vehicleID);

}
