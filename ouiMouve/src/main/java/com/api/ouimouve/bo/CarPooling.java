package com.api.ouimouve.bo;

import com.api.ouimouve.enumeration.CarPoolingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Entity representing a carpooling trip.
 * Mapped to a database table using JPA annotations.
 */
@Entity
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString automatically
public class CarPooling {

    /** Unique identifier for the carpooling. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented ID
    private Long id;

    /** The date and time when the carpooling is scheduled to start. */
    @NotNull // Required field
    private Date departure;

    /** The date and time when the carpooling is scheduled to arrive. */
    @NotNull // Required field
    private Date arrival;

    /** The status of the carpooling (e.g., PENDING, VALIDATED, CANCELLED). */
    @NotNull
    @Enumerated(EnumType.STRING) // Store the enum as a string in the database
    private CarPoolingStatus status;

    /** Duration of the carpooling in minutes. */
    @NotNull
    private Integer durationInMinutes;

    /** Distance of the carpooling in kilometers. */
    @NotNull
    private Integer distance;

    /** Address where the carpooling starts. */
    @ManyToOne // Many carpoolings can start at the same address
    @JoinColumn(name = "departure_address_id", nullable = false) // Foreign key column
    private Adress departureAdress;

    /** Address where the carpooling ends. */
    @ManyToOne // Many carpoolings can end at the same address
    @JoinColumn(name = "destination_address_id", nullable = false)
    private Adress destinationAdress;

    /** Vehicle used for the carpooling. */
    @ManyToOne // Many carpoolings can use the same vehicle
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /** User who organizes the carpooling. */
    @ManyToOne // Many carpoolings can be organized by the same user
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    /** Users who participate as passengers in the carpooling. */
    @OneToMany(mappedBy = "carPooling") // One carpooling can have multiple reservations
    private List<CarPoolingReservations> reservations;
}
