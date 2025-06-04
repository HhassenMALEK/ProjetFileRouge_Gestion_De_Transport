package com.api.ouimouve.bo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * VehicleClass
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    /**
     * Unique identifier for the repair record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Immatriculation of the vehicle
     */
    @Column(nullable = false)
    private String immatriculation;

    /**
     * number of seat of this vehicle
     */
    @Column(nullable = false)
    private Integer seats;

    /**
     * relationship with the Carpooling
     */
    @OneToMany (mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<CarPooling> carPoolings;


}


