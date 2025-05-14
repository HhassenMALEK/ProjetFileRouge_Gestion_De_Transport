package com.api.ouimouve.bo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Reparation entity class representing a repair record.
 * It contains fields for the repair ID, start date, end date, and motive.
 * The vehicle association is commented out for future implementation.
 */
@Entity
@Data
public class Reparation {
    /**
     * Unique identifier for the repair record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Start date of the repair.
     */
    @Column(nullable = false)
    private Date start;
    /**
     * End date of the repair.
     */
    @Column(nullable = false)
    private Date end;
    /**
     * Motive for the repair.
     */
    @Column(nullable = false)
    private String motive;

    @ManyToOne
    @JoinColumn(name = "service_vehicle_id", nullable = false)
    private ServiceVehicle serviceVehicle;
    /**
     * Vehicle ID associated with the repair.
     */
    private long vehicleId;
}
