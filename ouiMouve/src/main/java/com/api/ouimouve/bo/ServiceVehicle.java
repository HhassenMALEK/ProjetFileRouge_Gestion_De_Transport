package com.api.ouimouve.bo;


import com.api.ouimouve.enumeration.VehicleStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/** * ServiceVehicle
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("service")
public class ServiceVehicle extends Vehicle {
    /**
     * Status of the vehicle (available, in service, etc.)
     */
   private VehicleStatus status;

    /**
     * relationship with the reservation
     */
    @OneToMany(mappedBy = "servicelVehicle", fetch = FetchType.EAGER)
    private List<VehicleReservation> vehicleReservations;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @OneToMany (mappedBy = "serviceVehicle", fetch = FetchType.LAZY)
    private List<Reparation> reparations;

}
