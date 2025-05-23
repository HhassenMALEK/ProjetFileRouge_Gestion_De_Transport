package com.api.ouimouve.controller;

import com.api.ouimouve.dto.ServiceVehicleCreateDto;
import com.api.ouimouve.dto.ServiceVehicleDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.ServiceVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-vehicle")
public class ServiceVehicleController {
    @Autowired
    private ServiceVehicleService serviceVehicleService;

    /**
     * Gets a service vehicle by its ID
     *
     * @param id the ID of the service vehicle
     * @return the ServiceVehicleDto object
     * @throws RessourceNotFoundException if the vehicle does not exist
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a service vehicle by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ServiceVehicleDto getServiceVehicle(@PathVariable Long id) throws RessourceNotFoundException {
        return serviceVehicleService.getServiceVehicleById(id);
    }

    /**
     * Creates a new service vehicle
     *
     * @param serviceVehicleDto the service vehicle data
     * @return the created ServiceVehicleDto object
     * @throws InvalidRessourceException if the vehicle data is invalid
     */
    @PostMapping
    @Operation(summary = "Create a new service vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ServiceVehicleDto createServiceVehicle(@RequestBody @Valid ServiceVehicleCreateDto serviceVehicleDto) throws InvalidRessourceException {
        return serviceVehicleService.createServiceVehicle(serviceVehicleDto);
    }

    /**
     * Updates an existing service vehicle
     *
     * @param id the ID of the service vehicle to update
     * @param serviceVehicleDto the new service vehicle data
     * @return the updated ServiceVehicleDto object
     * @throws RessourceNotFoundException if the vehicle does not exist
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing service vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ServiceVehicleDto updateServiceVehicle(@PathVariable Long id, @RequestBody @Valid ServiceVehicleCreateDto serviceVehicleDto) throws RessourceNotFoundException {
        return serviceVehicleService.updateServiceVehicle(id, serviceVehicleDto);
    }

    /**
     * Deletes a service vehicle
     *
     * @param id the ID of the service vehicle to delete
     * @return the deleted ServiceVehicleDto object
     * @throws RessourceNotFoundException if the vehicle does not exist
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a service vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access required"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ServiceVehicleDto deleteServiceVehicle(@PathVariable Long id) throws RessourceNotFoundException {
        return serviceVehicleService.deleteServiceVehicle(id);
    }
}