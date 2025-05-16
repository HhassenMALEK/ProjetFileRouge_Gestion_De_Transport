package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.VehicleStatus;
import lombok.Data;

import java.util.Date;

@Data
public class VehicleReservationCreateDto {
    private Long serviceVehicleId;  // ID du véhicule de service
    private Long userId;            // Juste l'ID utilisateur
    private Date start;
    private Date end;
    private VehicleStatus status;
}
