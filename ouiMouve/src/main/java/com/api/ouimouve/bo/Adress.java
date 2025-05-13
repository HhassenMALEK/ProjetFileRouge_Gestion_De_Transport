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

//    @OneToOne
//    @JoinColumn(name = "site_id", referencedColumnName = "id")
    /**
     * site ID associated with the adress.
     */
    private long siteId;

    //@OneToMany(mappedBy = "departure", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Carpooling> departures;
    private long departures;

    //@OneToMany(mappedBy = "arrival", cascade = CascadeType.ALL, orphanRemoval = true)
    private long arrivals;

}
