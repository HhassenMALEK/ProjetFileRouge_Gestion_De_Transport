package com.api.ouimouve.repository;

import com.api.ouimouve.bo.CarPooling;
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
     * Finds all carpoolings with the specified status and departure date after a given date.
     *
     * @param status the status of the carpooling
     * @param date   the date to compare with departure time
     * @return a list of matching carpoolings
     */
    List<CarPooling> findByStatusAndDepartureAfter(CarPoolingStatus status, Date date);

    // Liste par statut et tri par date
    List<CarPooling> findByStatusOrderByDepartureAsc(CarPoolingStatus status);

    //List des covoiturages aprés une date
    List<CarPooling> findByDepartureAfter(Date date);

    // Liste des covoiturages organisés par un utilisateur
    List<CarPooling> findByOrganizerId(Long userId);

    // Liste par véhicule
    List<CarPooling> findByVehicleId(Long vehicleId);

    // Détail d’un covoiturage pour l’organisateur
    Optional<CarPooling> findByIdAndOrganizerId(Long id, Long organizerId);

    // Filtrage par statut, date, véhicule
    List<CarPooling> findByOrganizerIdAndStatus(Long userId, CarPoolingStatus status);

   // ==> a vérifier List<CarPooling> findByOrganizerIdAndDeparture(Long userId, Date departure);
    List<CarPooling> findByOrganizerIdAndVehicleId(Long userId, Long vehicleId);

    // Filtrage combiné
    List<CarPooling> findByOrganizerIdAndStatusAndDepartureAndVehicleId(
            Long userId, CarPoolingStatus status, Date departure, Long vehicleId);

    //find by status
    List<CarPooling> findByStatus(CarPoolingStatus status);


    // Vérifier chevauchement pour un utilisateur (création/modification)
    @Query("""
    SELECT c FROM CarPooling c 
    WHERE c.organizer.id = :organizerId 
    AND ((c.departure <= :arrival AND c.arrival >= :departure))
""")
    List<CarPooling> findOverlappingByOrganizer(
            @Param("organizerId") Long organizerId,
            @Param("departure") Date departure,
            @Param("arrival") Date arrival
    );

    //Vérifier chevauchement pour un véhicule
    @Query("""
    SELECT c FROM CarPooling c
    WHERE c.vehicle.id = :vehicleId
    AND c.departure < :end
    AND c.arrival > :start
""")
    List<CarPooling> findOverlappingCarPoolingByVehicle(
            @Param("vehicleId") Long vehicleId,
            @Param("start") Date start,
            @Param("end") Date end
    );

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

    @Query("""
    SELECT c FROM CarPooling c
    WHERE (:organizerId IS NULL OR c.organizer.id = :organizerId)
    AND (:status IS NULL OR c.status = :status)
    AND (:departure IS NULL OR c.departure = :departure)
    AND (:vehicleId IS NULL OR c.vehicle.id = :vehicleId)
""")
    List<CarPooling> filterCarpoolings(
            @Param("organizerId") Long organizerId,
            @Param("status") CarPoolingStatus status,
            @Param("departure") Date departure,
            @Param("vehicleId") Long vehicleId
    );



}
