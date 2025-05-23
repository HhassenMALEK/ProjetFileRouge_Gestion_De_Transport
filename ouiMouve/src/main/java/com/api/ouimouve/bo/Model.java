package com.api.ouimouve.bo;

import com.api.ouimouve.enumeration.VehicleCategory;
import com.api.ouimouve.enumeration.VehicleStatus;
import com.api.ouimouve.validation.ValidMaxNbSeats;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Reparation entity class representing a repair record.
 * It contains fields for the repair ID, start date, end date, and motive.
 * The vehicle association is commented out for future implementation.
 */
@Entity
@Data
public class Model {
    /**
     * Unique identifier for the repair record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Name of the model.
     */
    @Column(nullable = false)
    private String modelName;

    /**
     * Brand of the model.
     */
    @Column(nullable = false)
    private String mark;

    /**
     * URL of the model's photo.
     */
    @Column(nullable = false)
    private String photoURL;

    /**
     * type of the motor
     */
    @Column(nullable = true)
    private String motorType;

    /**
     * Category of the model.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private VehicleCategory category;

    /**
     * Consumption of CO2 in grams per kilometer.
     */
    @Column(nullable = true)
    private Integer CO2;

    /**
     * Number of seats in the model.
     */
    @Column(name = "seats_model", nullable = false)
    private Integer seatsModel;

    /**
     * Relationship with the Service Vehicle entity.
     */
    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
    private List<ServiceVehicle> serviceVehicles;

}

