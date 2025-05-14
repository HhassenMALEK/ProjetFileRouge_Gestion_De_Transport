package com.api.ouimouve.bo;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


/** * PersonalVehicle
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("personal")
public class PersonalVehicle extends Vehicle {
    /**
     * Color of the vehicle.
     */
    private String color;

    /**
     * Description of the vehicle
     */
    private String description;


    /**
     * relationship with the Carpooling
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
