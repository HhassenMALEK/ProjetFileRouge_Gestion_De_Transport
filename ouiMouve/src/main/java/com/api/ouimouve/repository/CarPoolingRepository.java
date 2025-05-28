package com.api.ouimouve.repository;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.bo.Site;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing CarPooling entities.
 * This interface extends JpaRepository to provide CRUD operations
 */
@Repository
public interface CarPoolingRepository extends JpaRepository<CarPooling, Long>{

    /**
     * Finds overlapping carpoolings for a given vehicle.
     * @param vehicleId the ID of the vehicle
     * @param start     the start date
     * @param end       the end date
     * @return a list of overlapping carpoolings
     */
    @Query("""
    SELECT c FROM CarPooling c
    WHERE c.vehicle.id = :vehicleId
    AND c.departure < :end
    AND c.arrival > :start
""")
    /**find over lapping carpooling by vehicle
     * @param vehicleId the ID of the vehicle
     * @param start     the start date
     * @param end       the end date
     * @return a list of overlapping carpoolings
     */
    List<CarPooling> findOverlappingCarPoolingByVehicle(
            @Param("vehicleId") Long vehicleId,
            @Param("start") Date start,
            @Param("end") Date end
    );

    /**
     * Finds overlapping carpoolings for a given vehicle excluding a specific ID.
     * @param vehicleId the ID of the vehicle
     * @param start     the start date
     * @param end       the end date
     * @param excludeId the ID to exclude from the results
     * @return a list of overlapping carpoolings
     */
    @Query("""
    SELECT c FROM CarPooling c 
    WHERE c.vehicle.id = :vehicleId 
    AND c.id <> :excludeId 
    AND c.departure < :end 
    AND c.arrival > :start
""")
    List<CarPooling> findOverlappingCarPoolingByVehicleExcludingId(
            @Param("vehicleId") Long vehicleId,
            @Param("start") Date start,
            @Param("end") Date end,
            @Param("excludeId") Long excludeId
    );

    /**
     * Finds overlapping carpoolings for a given organizer excluding a specific ID.
     * @param organizerId the ID of the organizer
     * @param start       the start date
     * @param end         the end date
     * @param excludeId   the ID to exclude from the results
     * @return a list of overlapping carpoolings
     */
    @Query("""
    SELECT c FROM CarPooling c 
    WHERE c.organizer.id = :organizerId 
    AND c.id <> :excludeId 
    AND c.departure < :end 
    AND c.arrival > :start
""")
    List<CarPooling> findOverlappingCarPoolingByOrganizer(
            @Param("organizerId") Long organizerId,
            @Param("start") Date start,
            @Param("end") Date end,
             @Param("excludeId") Long excludeId
    );

    /** Filter carpoolings based on multiple criteria.
     * @param organizerId the ID of the organizer (optional)
     * @param status      the status of the carpooling (optional)
     * @param vehicleId   the ID of the vehicle (optional)
     * @return a list of carpoolings matching the criteria
     */
    @Query("""
    SELECT c FROM CarPooling c
    WHERE (:organizerId IS NULL OR c.organizer.id = :organizerId)
    AND (:status IS NULL OR c.status = :status)
   AND (:startDate IS NULL OR c.departure >= :startDate)
    AND (:departureSiteId IS NULL OR c.departureSite.id= :departureSiteId)
    AND (:destinationSiteId IS NULL OR c.destinationSite.id = :destinationSiteId)
    AND (:vehicleId IS NULL OR c.vehicle.id = :vehicleId)
""")
    List<CarPooling> filterCarpoolings(
            @Param("organizerId") Long organizerId,
            @Param("status") CarPoolingStatus status,
            @Param("startDate") Date startDate,
            @Param("departureSiteId") Long departureSiteId,
            @Param("destinationSiteId") Long destinationSiteId,
            @Param("vehicleId") Long vehicleId
    );

}
