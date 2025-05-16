package com.api.ouimouve.bo;

import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import static jakarta.persistence.GenerationType.*;

/**
 * CarPoolingReservations entity class representing a carpooling reservation record.
 */
@Entity
@Data
public class CarPoolingReservations {
    /**
     * Unique identifier for the carpooling reservation.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    /**
     * Date of the reservation.
     */
    @Column(nullable = false)
    private Date date;
    /**
     * Status of the reservation.
     * Possible values: IN_PROGRESS, FINISHED, CANCELLED
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarPoolingReservationStatus status;
    /**
     * User ID associated with the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;
    /**
     * Carpooling ID associated with the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "carpooling_id", referencedColumnName = "id",nullable = false)
    private CarPooling carPooling;
}
