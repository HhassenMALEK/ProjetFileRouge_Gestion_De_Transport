package com.api.ouimouve.bo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Adress Class
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adress {

    /**
     * Unique identifier for an adress
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Label of this adress
     */
    @Column(nullable = false)
    private String label;

    /**
     * City of this adress
     */
    @Column(nullable = false)
    private String city;

    /**
     * Latitude of this adress
     */
    @Column(nullable = false)
    private float latX;

    /**
     * Longitude of this adress
     */
    @Column(nullable = false)
    private float longY;

    /**
     * The carpooling departures associated with this address
     */
    @OneToMany(mappedBy = "departureAdress")
    private List<CarPooling> departures;
    /**
     * The carpooling arrivals associated with this address
     */
    @OneToMany(mappedBy = "destinationAdress")
    private List<CarPooling> arrivals;

}
