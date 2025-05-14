package com.api.ouimouve.repository;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.bo.CarPoolingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for managing CarPooling entities.
 * This interface extends JpaRepository to provide CRUD operations
 */
@Repository
public interface CarPoolingRepository extends JpaRepository<CarPooling, Long>{

    /**
     * Finds all carpoolings with the specified status.
     *
     * @param status the status of the carpooling
     * @return a list of car poolings with the specified status
     */
    List<CarPooling> findByStatus(CarPoolingStatus status);

    /**
     * Finds all carpoolings scheduled to depart after the specified date
     *
     * @param date the date to compare with departure time
     * @return a list of carpoolings with departure dates after the specified date
     */
    List<CarPooling> findByDepartureAfter(Date date);

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


}
