package com.api.ouimouve.bo;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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


    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "address_id", referencedColumnName = "id")
    //private Address address;

    //@OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    //private List<Vehicle> vehicles;

    private String AdresseID;
    private Long vehiculeID;
}
