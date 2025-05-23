package com.api.ouimouve.service;

import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.bo.Site;
import com.api.ouimouve.enumeration.VehicleStatus;
import com.api.ouimouve.mapper.AdressMapper;
import com.api.ouimouve.dto.SiteCreateDto;
import com.api.ouimouve.dto.SiteResponseDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.SiteMapper;
import com.api.ouimouve.repository.ServiceVehicleRepository;
import com.api.ouimouve.repository.SiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Service class responsible for all business logic related to Site entities.
 * This includes validation, creation, update, deletion, and transformation to/from DTOs.
 */
@Service
@Transactional
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

    /**
     * Service for managing vehicle-related data.
     */
    @Autowired
    private ServiceVehicleRepository serviceVehicleRepository;

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
     * @throws InvalidRessourceException  if validation fails.
     * @throws RessourceNotFoundException if the referenced address is not found.
     */
    public SiteResponseDto createSite(SiteCreateDto dto) {
        validateSite(dto, null);
        Site site = siteMapper.toSite(dto);
        populateEntityReferences(site, dto);
        // Association centralisée des véhicules
        if (dto.getVehicleIds() != null && !dto.getVehicleIds().isEmpty()) {
            associateVehiclesWithSite(site, dto.getVehicleIds());
        }
        return siteMapper.toSiteResponseDto(siteRepository.save(site));
    }

    /**
     * Updates an existing site with new values.
     *
     * @param id  the ID of the site to update.
     * @param dto the updated site data.
     * @return the updated Site as a SiteResponseDto.
     * @throws RessourceNotFoundException if the site or referenced address does not exist.
     * @throws InvalidRessourceException  if validation fails.
     */
    public SiteResponseDto updateSite(Long id, SiteCreateDto dto) {
        validateSite(dto, id);
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Site not found with ID: " + id));
        site.setName(dto.getName());
        populateEntityReferences(site, dto);

        // Utiliser la même méthode que pour la création
        if (dto.getVehicleIds() != null) {
            // D'abord dissocier les véhicules actuels
            site.getVehiclesServices().forEach(v -> v.setSite(null));
            // Puis associer les nouveaux véhicules
            associateVehiclesWithSite(site, dto.getVehicleIds());
        }

        return siteMapper.toSiteResponseDto(siteRepository.save(site));
    }

    public SiteResponseDto deleteSite(Long id) {
        // Recherche du site ou exception
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Site not found with ID: " + id));

        // Récupération des véhicules liés
        List<ServiceVehicle> vehicles = serviceVehicleRepository.findAllBySite_Id(id);

        // Vérifie si l'un des véhicules n'est pas ENABLED
        boolean hasUnavailableVehicles = vehicles.stream()
                .anyMatch(vehicle -> vehicle.getStatus() != VehicleStatus.ENABLED);
        if (hasUnavailableVehicles) {
            throw new InvalidRessourceException("Impossible de supprimer ce site : un ou plusieurs véhicules sont réservés ou désactivés.");
        }

        // Dissocie les véhicules avant suppression
        vehicles.forEach(vehicle -> vehicle.setSite(null));
        serviceVehicleRepository.saveAll(vehicles);

        // Important: vider également la liste des véhicules dans l'objet site
        site.setVehiclesServices(new ArrayList<>());

        // Suppression du site
        siteRepository.delete(site);

        return siteMapper.toSiteResponseDto(site);
    }

    /**
     * Validates the data received for site creation or update.
     *
     * @param dto the DTO to validate.
     * @throws InvalidRessourceException  if required fields are missing or invalid.
     * @throws RessourceNotFoundException if referenced address does not exist.
     */
    private void validateSite(SiteCreateDto dto, Long siteId) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new InvalidRessourceException("Site name is required.");
        }
        if (dto.getAdressId() == null) {
            throw new InvalidRessourceException("Address ID is required.");
        }
        adressService.getAdressById(dto.getAdressId());

        // Vérifier l'unicité du nom + adresseId
        siteRepository.findByNameAndAdressId(dto.getName(), dto.getAdressId())
                .ifPresent(existing -> {
                    // Si on est en update, il faut ignorer le site lui-même
                    if (siteId == null || !existing.getId().equals(siteId)) {
                        throw new InvalidRessourceException("This site already exists!");
                    }
                });

    }

    /**
     * Populates the address reference for a Site entity from a DTO.
     *
     * @param site the site entity to update.
     * @param dto  the DTO containing the address ID.
     * @throws RessourceNotFoundException if the address does not exist.
     */
    private void populateEntityReferences(Site site, SiteCreateDto dto) {
        site.setAdress(adressMapper.toAdress(adressService.getAdressById(dto.getAdressId())));
    }

    private void associateVehiclesWithSite(Site site, List<Long> vehicleIds) {
        List<ServiceVehicle> vehicles = vehicleIds.stream()
                .map(id -> {
                    ServiceVehicle vehicle = serviceVehicleRepository.findById(id)
                            .orElseThrow(() -> new RessourceNotFoundException("Vehicle not found with ID: " + id));

                    // Vérifier si le véhicule est déjà assigné à un autre site
                    if (vehicle.getSite() != null && !vehicle.getSite().getId().equals(site.getId())) {
                        throw new InvalidRessourceException("Vehicle with ID " + id + " is already assigned to another site.");
                    }

                    return vehicle;
                })
                .collect(Collectors.toList());

        // Définir la relation bidirectionnelle
        vehicles.forEach(v -> v.setSite(site));
        serviceVehicleRepository.saveAll(vehicles);
        site.setVehiclesServices(vehicles);
    }


}