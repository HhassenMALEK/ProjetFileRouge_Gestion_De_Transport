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

    @OneToOne()
    @JoinColumn(name = "adress_id", referencedColumnName = "id")
    private Adress adress;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<ServiceVehicle> vehiclesServices;
}
