package com.api.ouimouve.bo;

import com.api.ouimouve.enumeration.VehicleStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
    * Represents a vehicle reservation.
    * This class is used to store information about a vehicle reservation, including the start and end dates,
    * the motive for the reservation, and the associated vehicle ID.
    */
@Entity
@Data
public class VehicleReservation {
    /**
     * Unique identifier for the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Vehicle ID associated with the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "service_vehicle_id", nullable = false)
    private ServiceVehicle servicelVehicle;
    @Column(nullable = false)
    private long vehicleID;

    /**
     * User ID associated with the reservation.
     */
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    @Column(nullable = false)
    private long userID;

    /**
     * Start date of the reservation.
     */
    @Column(nullable = false)
    private Date start;

    /**
     * End date of the reservation.
     */
    @Column(nullable = false)
    private Date end;


    /**
     * Motive for the reservation.
     * Possible values: "business", "personal", "other".
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

}
