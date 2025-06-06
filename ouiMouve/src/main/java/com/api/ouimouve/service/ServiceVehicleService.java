package com.api.ouimouve.service;


import com.api.ouimouve.bo.Model;
import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.ServiceVehicleCreateDto;
import com.api.ouimouve.dto.ServiceVehicleDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.VehicleMapper;
import com.api.ouimouve.repository.ModelRepository;
import com.api.ouimouve.repository.ServiceVehicleRepository;
import com.api.ouimouve.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service for managing Service vehicles.
 * Handles CRUD operations and specific service vehicle functionalities.
 */
@Service
public class ServiceVehicleService {

    @Autowired
    private ServiceVehicleRepository serviceVehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private ModelRepository modelRepository;

    /**
     * Get a service vehicle by its ID.
     * 
     * @param id The ID of the service vehicle to retrieve
     * @return The service vehicle DTO if found, null otherwise
     */
    public ServiceVehicleDto getServiceVehicleById(Long id) {
        return serviceVehicleRepository.findById(id)
                .map(vehicle -> vehicleMapper.toDto(vehicle))
                .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found"));
    }

    /**
     * Get all service vehicles associated with a specific site.
     *
     * @param siteId The site for which to retrieve service vehicles
     * @return A list of service vehicle DTOs associated with the site
     */

    public List<ServiceVehicleDto> getAllServiceVehiclesBySite(long siteId) {
        return serviceVehicleRepository.findAllBySiteId(siteId).stream()
                .map(vehicleMapper::toDto)
                .toList();
    }

    /**
     * Create a new service vehicle.
     * 
     * @param dto The DTO containing the service vehicle data
     * @return The created service vehicle DTO
     * @throws RessourceNotFoundException if the referenced user doesn't exist
     */
    public ServiceVehicleDto createServiceVehicle(ServiceVehicleCreateDto dto) {
        if (vehicleService.doesImmatriculationExist(dto.getImmatriculation())) {
            throw new InvalidRessourceException("Vehicle with this immatriculation already exists");
        }
        // TODO : revoir les controles à la création
        ServiceVehicle vehicle = vehicleMapper.toEntity(dto);
        return vehicleMapper.toDto(serviceVehicleRepository.save(vehicle));
    }

    /**
     * Update an existing service vehicle.
     * 
     * @param id  The ID of the service vehicle to update
     * @param dto The DTO containing the updated data
     * @return The updated service vehicle DTO
     * @throws RessourceNotFoundException if the vehicle doesn't exist
     */
    public ServiceVehicleDto updateServiceVehicle(Long id, ServiceVehicleCreateDto dto) {
        ServiceVehicle vehicle = serviceVehicleRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found"));
        if (!vehicle.getImmatriculation().equals(dto.getImmatriculation())) {
            if (vehicleService.doesImmatriculationExist(dto.getImmatriculation())) {
                throw new InvalidRessourceException("Vehicle with this immatriculation already exists");
            }
        }
        // TODO: Revoir controles et champs à mettre à jour
        vehicle = vehicleMapper.toEntity(dto);
        return vehicleMapper.toDto(serviceVehicleRepository.save(vehicle));
    }

    /**
     * Delete a service vehicle by its ID.
     * 
     * @param id The ID of the service vehicle to delete
     * @return The deleted service vehicle DTO
     */
    public ServiceVehicleDto deleteServiceVehicle(Long id) {
        ServiceVehicle vehicle = serviceVehicleRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found"));
        if (vehicle != null) {
            serviceVehicleRepository.deleteById(id);
        }
        // TODO:Revoir controle avant suppression
        return vehicleMapper.toDto(vehicle);
    }

    /**
     * Get all service vehicles.
     */
    public List<ServiceVehicleDto> getAllServiceVehicles()
    {    return serviceVehicleRepository.findAll().stream()
            .map(vehicleMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     *  Find all service vehicles by optional filters
     *  * @param status the status of the vehicle to filter by (optional)
     *  * @param modelName the name of the model to filter by (optional)
     *  * @param siteName the name of the site to filter by (optional)
     *  * @param seats the number of seats to filter by (optional)
     *  * @return a list of ServiceVehicleDto objects that match the filters
     */
    public List<ServiceVehicleDto> findAllServiceVehiclesByFilters(
            String status, String modelName, String siteName, Integer seats) {

        Long siteId = null;
        if(siteName != null){
            Site site = siteRepository.findByName(siteName)
                    .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable : " + siteName));
            siteId = site.getId();
        }
        Long modelId = null;
        if(modelName != null){
            Model model = modelRepository.findByModelName(modelName).stream()
                          .findFirst()
                          .orElseThrow(() -> new RessourceNotFoundException("Model not found: " + modelName));
            modelId = model.getId();
        }

        List<ServiceVehicle> vehicles = serviceVehicleRepository.findAllByFilters(siteId, modelId,seats, status);
        return vehicles.stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

}