package com.api.ouimouve.service;

import com.api.ouimouve.bo.PersonalVehicle;
import com.api.ouimouve.dto.PersonalVehicleCreateDto;
import com.api.ouimouve.dto.PersonalVehicleDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.VehicleMapper;
import com.api.ouimouve.repository.PersonalVehicleRepository;
import com.api.ouimouve.utils.AuthContext;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing personal vehicles.
 * Handles CRUD operations and specific personal vehicle functionalities.
 */
@Service
@Slf4j
public class PersonalVehicleService {

    @Autowired
    private PersonalVehicleRepository personalVehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AuthContext authContext;

    @Autowired
    private VehicleMapper vehicleMapper;

    /**
     * Get a personal vehicle by its ID.
     * 
     * @param id The ID of the personal vehicle to retrieve
     * @return The personal vehicle DTO if found, null otherwise
     */
    public PersonalVehicleDto getPersonalVehicleById(Long id) {
        PersonalVehicleDto res = personalVehicleRepository.findById(id)
                .map(vehicle -> vehicleMapper.toDto(vehicle))
                .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found"));
        log.info("Retrieved personal vehicle with ID: {}", res);
        return res;
    }
    /**
     * Get all personal vehicles for a specific user.
     * @return List of personal vehicles owned by the user
     */
    public List<PersonalVehicleDto> getPersonalVehiclesByUserId() {
        List<PersonalVehicleDto> vehicles = personalVehicleRepository.findByUserId(authContext.getCurrentUser().getId()).stream()
                .map(vehicle -> vehicleMapper.toDto(vehicle))
                .toList();
        if (vehicles.isEmpty()) {
            throw new RessourceNotFoundException("No personal vehicles found for this user");
        } else {
            return vehicles;
        }
    }

    /**
     * Create a new personal vehicle.
     * 
     * @param dto The DTO containing the personal vehicle data
     * @return The created personal vehicle DTO
     * @throws RessourceNotFoundException if the referenced user doesn't exist
     */
    public PersonalVehicleDto createPersonalVehicle(PersonalVehicleCreateDto dto) {
        if (vehicleService.doesImmatriculationExist(dto.getImmatriculation())) {
            throw new InvalidRessourceException("Vehicle with this immatriculation already exists");
        }
        dto.setUserId(authContext.getCurrentUser().getId());
        PersonalVehicle vehicle = vehicleMapper.toEntity(dto);
        return vehicleMapper.toDto(personalVehicleRepository.save(vehicle));
    }

    /**
     * Update an existing personal vehicle.
     * 
     * @param id The ID of the personal vehicle to update
     * @param dto The DTO containing the updated data
     * @return The updated personal vehicle DTO
     * @throws RessourceNotFoundException if the vehicle doesn't exist
     */
    public PersonalVehicleDto updatePersonalVehicle(Long id, PersonalVehicleCreateDto dto) {
        PersonalVehicle vehicle = personalVehicleRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found"));
        if (!vehicle.getImmatriculation().equals(dto.getImmatriculation())) {
            if (vehicleService.doesImmatriculationExist(dto.getImmatriculation())) {
                throw new InvalidRessourceException("Vehicle with this immatriculation already exists");
            }
        }
        // TODO: Si véhicule réservé ou dans un covoit, on ne peut pas modifier le nombre de places
        dto.setUserId(authContext.getCurrentUser().getId());
        dto.setSeats(vehicle.getSeats());
        dto.setId(id);
        vehicle = vehicleMapper.toEntity(dto);
        return vehicleMapper.toDto(personalVehicleRepository.save(vehicle));
    }

    /**
     * Delete a personal vehicle by its ID.
     * 
     * @param id The ID of the personal vehicle to delete
     * @return The deleted personal vehicle DTO
     */
    public PersonalVehicleDto deletePersonalVehicle(Long id) {
        PersonalVehicle vehicle = personalVehicleRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found"));
        if (vehicle != null) {
            personalVehicleRepository.deleteById(id);
        }
        //TODO: Si véhicule réservé ou dans un covoit, on ne peut pas le supprimer
        return vehicleMapper.toDto(vehicle);
    }
}