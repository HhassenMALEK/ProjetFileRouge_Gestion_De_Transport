package com.api.ouimouve.service;

import com.api.ouimouve.bo.Site;
import com.api.ouimouve.mapper.AdressMapper;
import com.api.ouimouve.dto.SiteCreateDto;
import com.api.ouimouve.dto.SiteResponseDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.SiteMapper;
//import com.api.ouimouve.repository.ServiceVehicleRepository;
import com.api.ouimouve.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for all business logic related to Site entities.
 * This includes validation, creation, update, deletion, and transformation to/from DTOs.
 */
@Service
public class SiteService {

    /**
     * Repository for accessing and managing Site data in the database.
     */
    @Autowired
    private SiteRepository siteRepository;
    /**
     * Mapper for converting Site entities to SiteResponseDto and vice versa.
     */
    @Autowired
    private SiteMapper siteMapper;
    /**
     * Service for retrieving and validating address-related data.
     */
    @Autowired
    private AdressService adressService;
    /**
     * Mapper for converting address entities and DTOs.
     */
    @Autowired
    private AdressMapper adressMapper;

    // @Autowired
    // private ServiceVehicleRepository serviceVehicleRepository;

    /**
     * Retrieves all sites stored in the system.
     *
     * @return a list of SiteResponseDto representing all existing sites.
     */
    public List<SiteResponseDto> getAllSites() {
        return siteRepository.findAll().stream()
                .map(siteMapper::toSiteResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a site by its unique identifier.
     *
     * @param id the ID of the site to retrieve.
     * @return the corresponding SiteResponseDto.
     * @throws RessourceNotFoundException if no site is found with the given ID.
     */
    public SiteResponseDto getSiteById(Long id) {
        return siteRepository.findById(id)
                .map(siteMapper::toSiteResponseDto)
                .orElseThrow(() -> new RessourceNotFoundException("Site not found with ID: " + id));
    }

    /**
     * Creates a new site in the system.
     * Performs validation and populates all required entity relationships.
     *
     * @param dto the data used to create the new site.
     * @return the created Site as a SiteResponseDto.
     * @throws InvalidRessourceException if validation fails.
     * @throws RessourceNotFoundException if the referenced address is not found.
     */
    public SiteResponseDto createSite(SiteCreateDto dto) {
        validateSite(dto);
        Site site = siteMapper.toSite(dto);
        populateEntityReferences(site, dto);

        // Optional vehicle association (currently commented out)
        // if (dto.getVehicleIds() != null && !dto.getVehicleIds().isEmpty()) {
        //     List<ServiceVehicle> vehicles = dto.getVehicleIds().stream()
        //             .map(id -> serviceVehicleRepository.findById(id)
        //                 .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found with ID: " + id)))
        //             .collect(Collectors.toList());
        //     site.setVehiclesServices(vehicles);
        // }

        return siteMapper.toSiteResponseDto(siteRepository.save(site));
    }

    /**
     * Updates an existing site with new values.
     *
     * @param id  the ID of the site to update.
     * @param dto the updated site data.
     * @return the updated Site as a SiteResponseDto.
     * @throws RessourceNotFoundException if the site or referenced address does not exist.
     * @throws InvalidRessourceException if validation fails.
     */
    public SiteResponseDto updateSite(Long id, SiteCreateDto dto) {
        validateSite(dto);
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Site not found with ID: " + id));
        site.setName(dto.getName());
        populateEntityReferences(site, dto);

        // Optional vehicle update logic (currently commented out)
        // if (dto.getVehicleIds() != null) {
        //     List<ServiceVehicle> vehicles = dto.getVehicleIds().stream()
        //             .map(vid -> serviceVehicleRepository.findById(vid)
        //                 .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found with ID: " + vid)))
        //             .collect(Collectors.toList());
        //     site.setVehiclesServices(vehicles);
        // }

        return siteMapper.toSiteResponseDto(siteRepository.save(site));
    }

    /**
     * Deletes a site by its ID.
     *
     * @param id the ID of the site to delete.
     * @return the deleted site as a SiteResponseDto.
     * @throws RessourceNotFoundException if the site does not exist.
     */
    public SiteResponseDto deleteSite(Long id) {
        SiteResponseDto dto = getSiteById(id);
        siteRepository.deleteById(id);
        return dto;
    }

    /**
     * Validates the data received for site creation or update.
     *
     * @param dto the DTO to validate.
     * @throws InvalidRessourceException if required fields are missing or invalid.
     * @throws RessourceNotFoundException if referenced address does not exist.
     */
    private void validateSite(SiteCreateDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidRessourceException("Site name is required.");
        }

        if (dto.getAdressId() == null) {
            throw new InvalidRessourceException("Address ID is required.");
        }
        // This will throw if the address does not exist
        adressService.getAdressById(dto.getAdressId());
    }

    /**
     * Populates the address reference for a Site entity from a DTO.
     *
     * @param site the site entity to update.
     * @param dto  the DTO containing the address ID.
     * @throws RessourceNotFoundException if the address does not exist.
     */
    private void populateEntityReferences(Site site, SiteCreateDto dto) {
        // Convertir l'AdressDto en Adress avant de l'assigner
        site.setAdress(adressMapper.toAdress(adressService.getAdressById(dto.getAdressId())));
    }

}
