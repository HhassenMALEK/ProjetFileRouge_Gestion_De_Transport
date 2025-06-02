package com.api.ouimouve.bo;

import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
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
@Data
public class CarPooling {

    /** Unique identifier for the carpooling. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The date and time when the carpooling is scheduled to start. */
    @NotNull
    private Date departure;

    /** The status of the carpooling (e.g., PENDING, VALIDATED, CANCELLED). */
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarPoolingStatus status;

    /** Duration of the carpooling in minutes. */
    @NotNull
    private Integer durationInMinutes;

    /** Distance of the carpooling in kilometers. */
    @NotNull
    private Integer distance;

    /** Address where the carpooling starts. */
    @ManyToOne
    @JoinColumn(name = "departure_site_id", nullable = false)
    private Site departureSite;

    /** Address where the carpooling ends. */
    @ManyToOne
    @JoinColumn(name = "destination_site_id", nullable = false)
    private Site destinationSite;

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

    /** Maximum number of passengers allowed in the carpooling. */
    public Long getNbSeatAvailable() {
        return vehicle.getSeats() - 1 - reservations.stream()
                .filter(reservation -> reservation.getStatus() != CarPoolingReservationStatus.CANCELLED)
                .count();
    }
}
