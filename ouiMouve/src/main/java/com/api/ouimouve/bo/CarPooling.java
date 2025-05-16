package com.api.ouimouve.bo;

import com.api.ouimouve.enumeration.CarPoolingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class CarPooling {

    /** Unique identifier for the carpooling. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The date and time when the carpooling is scheduled to start. */
    @NotNull
    private Date departure;

    /** The date and time when the carpooling is scheduled to arrive. */
    @NotNull
    private Date arrival;

    /**
     * The status of the carpooling.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarPoolingStatus status;

    /** Duration of the carpooling in minutes. */
    @NotNull
    private Integer durationInMinutes;

    /** Distance of the carpooling in kilometers. */
    @NotNull
    private Integer distance;



    /** Adress where the carpooling starts. */
    @ManyToOne
    @JoinColumn(name = "departure_address_id", nullable = false)
    private Adress departureAdress;

    /** Adress where the carpooling ends. */
    @ManyToOne
    @JoinColumn(name = "destination_address_id", nullable = false)
    private Adress destinationAdress;

    /** Vehicle used for the carpooling. */
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /** User who organizes the carpooling. */
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    /** Users who participate as passengers in the carpooling. */
    @OneToMany(mappedBy = "carPooling")
    private List<CarPoolingReservations> reservations;
}
