package com.api.ouimouve.bo;


import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity class representing a Site.
 * This class is used to map the Site entity to the database table.
 * It contains fields for the site ID, name, and other related entities.
 */
@Entity
@Data
public class Site {
    /**
     * Identifier of the site
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the site
     */
    @Column(nullable = false)
    private String name;

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


}
