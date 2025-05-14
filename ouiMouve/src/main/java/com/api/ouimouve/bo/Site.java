package com.api.ouimouve.bo;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


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
