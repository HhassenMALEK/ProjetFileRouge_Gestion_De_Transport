package com.api.ouimouve.bo;

import com.api.ouimouve.enumeration.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * User entity representing a user in the system.
 */
@Entity
@Data
public class User {
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * First name of the user.
     */
    @Column(nullable = false, length = 50)
    private String firstName;
    /**
     * Last name of the user.
     */
    @Column(nullable = false, length = 50)
    private String lastName;
    /**
     * Email address of the user it is used as the username in the app.
     */
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    /**
     * Password of the user.
     */
    @Column(nullable = false)
    private String password;
    /**
     * Phone number of the user.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    /**
     * License number of the user. It can be null, users are not required to have a vehicle.
     */
    @Column(unique = true, nullable = false, length = 20)
    private String licenseNumber;
    /**
     * List of carpooling reservations made by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarPoolingReservations> carPoolingReservations;
    /**
     * List of carpoolings organized by the user.
     */
    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarPooling> organizedCarPoolings;
    /**
     * List of personal vehicles associated with the user.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<PersonalVehicle> personalVehicles;
    /**
     * List of vehicle reservations made by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VehicleReservation> vehicleReservations;

}
