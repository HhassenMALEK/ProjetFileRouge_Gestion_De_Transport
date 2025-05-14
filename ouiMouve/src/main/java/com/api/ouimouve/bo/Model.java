package com.api.ouimouve.bo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
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
    @Column(nullable = true)
    private Integer category;

    /**
     * Consumption of CO2 in grams per kilometer.
     */
    @Column(nullable = true)
    private Integer CO2;

    /**
     * Number of places in the model.
     */
    @Column(nullable = false)
    private Integer placesModel;


// TODO à compléter si nécessaire de mettre la liste

// A voir si c'est utile
//    @ManyToOne
//    @JoinColumn(name = "model_id", nullable = false)

   // private List<Vehicle> vehicles;

}

