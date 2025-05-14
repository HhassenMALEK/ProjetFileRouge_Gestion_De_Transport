package com.api.ouimouve.controller;

import com.api.ouimouve.dto.VehicleReservationDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class VehicleReservationController {
    @Autowired
    private ReservationService reservationService;

    /**
     * Get all reservations for a vehicle
     *
     * @param vehicleId the ID of the vehicle
     * @return a list of VehicleReservationDto objects
     * @throws RessourceNotFoundException if no reservations are found for the vehicle
     */
    @GetMapping("/list/{vehicleId}")
    @Operation(summary = "Get all reservations for a vehicle", responses = {
            @ApiResponse(responseCode = "200", description = "List of reservations for the vehicle"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No reservations found for this vehicle"),
            @ApiResponse(responseCode = "500", description = "Internal Serveur Error") })
    public List<VehicleReservationDto>  getAllReservationsByVehicle(@PathVariable long vehicleId) throws RessourceNotFoundException {
        // TODO test l'existence du véhicule
        List<VehicleReservationDto> reservations = reservationService.getAllReservationsByVehicle(vehicleId);
        if (reservations.isEmpty()) {
            throw new RessourceNotFoundException("No reservations found for vehicle ID: " + vehicleId);
        }
        return reservations;
    }

    /**
     * Get all reservations
     *
     * @return a list of VehicleReservationDto objects
     * @throws RessourceNotFoundException if no reservations are found
     */
    @GetMapping("/list/user/{UserID}")
    @Operation(summary = "Get all reservations for a user", responses = {
            @ApiResponse(responseCode = "200", description = "List of reservations for the user"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No reservations found for this user"),
            @ApiResponse(responseCode = "500", description = "Internal Serveur Error") })
    public List<VehicleReservationDto>  getAllReservationsByUser(@PathVariable long UserID) throws RessourceNotFoundException {
        // TODO test l'existence du véhicule
        List<VehicleReservationDto> reservations = reservationService.getAllReservationsByUser(UserID);
        if (reservations.isEmpty()) {
            throw new RessourceNotFoundException("No reservations found for user ID: " + UserID);
        }
        return reservations;
    }

    /**
     * Get a reservation by its ID
     *
     * @param id the ID of the reservation
     * @return the VehicleReservationDto object
     * @throws RessourceNotFoundException if the reservation does not exist
     */
    @Operation(summary = "Get a reservation by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Reservation found"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No reservations found"),
            @ApiResponse(responseCode = "500", description = "Internal Serveur Error") })
    @GetMapping("/{id}")
    public VehicleReservationDto getReservationById(@PathVariable long id) throws RessourceNotFoundException {
        VehicleReservationDto reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            throw new RessourceNotFoundException("The reservation does not exist");
        }
        return reservation;
    }

    /**
     * Delete a reservation by its ID
     *
     * @param id the ID of the reservation
     * @return the deleted VehicleReservationDto object
     * @throws RessourceNotFoundException if the reservation does not exist
     */
    @Operation(summary = "Delete a reservation by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "No reservations found"),
            @ApiResponse(responseCode = "500", description = "Internal Serveur Error") })
    @DeleteMapping("/{id}")
    public VehicleReservationDto deleteReservation(@PathVariable long id) throws RessourceNotFoundException {
        VehicleReservationDto reservation = reservationService.deleteReservation(id);
        if (reservation == null) {
            throw new RessourceNotFoundException("The reservation does not exist");
        }
        return reservation;
    }

    /**
     * Update a reservation by its ID
     *
     * @param id the ID of the reservation
     * @param reservationDto the updated VehicleReservationDto object
     * @return the updated VehicleReservationDto object
     * @throws RessourceNotFoundException if the reservation does not exist
     */
    @Operation(summary = "Update a reservation by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Reservation updated"),
            @ApiResponse(responseCode = "403", description = "access required"),
            @ApiResponse(responseCode = "404", description = "No reservations found"),
            @ApiResponse(responseCode = "500", description = "Internal Serveur Error") })
    @PatchMapping("/{id}")
    public VehicleReservationDto updateReservation(@PathVariable long id, @RequestBody VehicleReservationDto reservationDto) throws RessourceNotFoundException {
        VehicleReservationDto updatedReservation = reservationService.updateReservation(id, reservationDto);
        if (updatedReservation == null) {
            throw new RessourceNotFoundException("The reservation does not exist");
        }
        return updatedReservation;
    }

    /**
     * Create a new reservation
     *
     * @param reservationDto the VehicleReservationDto object to create
     * @return the created VehicleReservationDto object
     * @throws RessourceNotFoundException if the vehicle does not exist
     */
    @Operation(summary = "Create a reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Reservation created"),
            @ApiResponse(responseCode = "403", description = "access required"),
            @ApiResponse(responseCode = "404", description = "Aucune idée ??"),
            @ApiResponse(responseCode = "500", description = "Internal Serveur Error") })
    @PostMapping()
    public VehicleReservationDto createReservation(@RequestBody VehicleReservationDto reservationDto) throws RessourceNotFoundException {
        // TODO test l'existence du véhicule
        return reservationService.createReservation(reservationDto);
    }
}
