package com.api.ouimouve.bo;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
     * Address associated with the site
     */
    @OneToOne()
    @JoinColumn(name = "adress_id" ,nullable = false)
    private Adress adress;

    /**
     * List of vehicles associated with the site
     */
    @OneToMany(mappedBy = "site")
    private List<ServiceVehicle> vehiclesServices;
}
