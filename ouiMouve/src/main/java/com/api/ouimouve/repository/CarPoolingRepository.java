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

    /**
     * Finds all carpoolings ordered by departure time ascending for a given status.
     *
     * @param status the status of the carpooling
     * @return an ordered list of carpoolings
     */
    List<CarPooling> findByStatusOrderByDepartureAsc(CarPoolingStatus status);


    // Liste des covoiturages par statut
    List<CarPooling> findByStatus(CarPoolingStatus status);

    //List des covoiturages aprés une date
    List<CarPooling> findByDepartureAfter(Date date);

    // Liste des covoiturages organisés par un utilisateur
    List<CarPooling> findByOrganizerId(Long userId);

    // Liste par véhicule
    List<CarPooling> findByVehicleId(Long vehicleId);

    // Détail d’un covoiturage pour l’organisateur
    Optional<CarPooling> findByIdAndOrganizerId(Long id, Long organizerId);

    // Supprimer seulement si statut EN_COURS
    void deleteByIdAndStatus(Long id, CarPoolingStatus status);

    // Filtrage par statut, date, véhicule
//    List<CarPooling> findByOrganizerIdAndStatus(Long userId, CarPoolingStatus status);
//    List<CarPooling> findByOrganizerIdAndDepartureDate(Long userId, LocalDate date);
//    List<CarPooling> findByOrganizerIdAndVehicleId(Long userId, Long vehicleId);

    // Filtrage combiné
//    List<CarPooling> findByOrganizerIdAndStatusAndDepartureDateAndVehicleId(
//            Long userId, CarPoolingStatus status, LocalDate date, Long vehicleId);



    // Vérifier chevauchement pour un utilisateur (création/modification)
//    @Query("SELECT c FROM CarPooling c WHERE c.organizer.id = :userId AND c.departure = :date AND c.departureTime = :time AND (:excludeId IS NULL OR c.id <> :excludeId)")
//    List<CarPooling> findOverlappingCarPoolingForUser(@Param("userId") Long userId, @Param("date") LocalDate date, @Param("time") LocalTime time, @Param("excludeId") Long excludeId);

    // Vérifier chevauchement pour un véhicule
//    @Query("SELECT c FROM CarPooling c WHERE c.vehicle.id = :vehicleId AND c.departure = :date AND c.departureTime = :time AND (:excludeId IS NULL OR c.id <> :excludeId)")
//    List<CarPooling> findOverlappingCarPoolingForVehicle(@Param("vehicleId") Long vehicleId, @Param("date") LocalDate date, @Param("time") LocalTime time, @Param("excludeId") Long excludeId);



    // Liste par statut et tri par date
//    List<CarPooling> findByStatusOrderByDepartureDateAsc(CarPoolingStatus status);



    // Liste par date
    //List<CarPooling> findByDepartureDate(Date date);




}
