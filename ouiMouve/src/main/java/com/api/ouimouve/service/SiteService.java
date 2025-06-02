package com.api.ouimouve.service;

import com.api.ouimouve.bo.Site;

import com.api.ouimouve.dto.SiteCreateDto;
import com.api.ouimouve.dto.SiteResponseDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.SiteMapper;
import com.api.ouimouve.repository.SiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ServiceVehicleService serviceVehicleService;

    @Autowired
    private CarPoolingService carPoolingService;

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
        return siteMapper.toSiteResponseDto(siteRepository.save(site));
    }

    public SiteResponseDto deleteSite(Long id) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Site not found with ID: " + id));

        /**
         * Checks if the site is referenced by any service vehicles.
         */
        boolean siteUsedByServiceVehicle = serviceVehicleService.getAllServiceVehiclesBySite(id).isEmpty();
        if (!siteUsedByServiceVehicle) {
            throw new InvalidRessourceException("Impossible de supprimer ce site : un ou plusieurs véhicules le référence");
        }
        /**
         * Checks if the site is referenced by any carPooling departure or destination.
         */
        boolean siteUsedByCarPooling = carPoolingService.getCarPoolingByFilter(null, null,null,null, site.getName(),null, null, null).isEmpty() &&
                carPoolingService.getCarPoolingByFilter(null, null,null,
                        null,null,site.getName(), null, null).isEmpty();
        if (!siteUsedByCarPooling) {
            throw new InvalidRessourceException("Impossible de supprimer ce site : un ou plusieurs covoiturages le référence");
        }
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
        siteRepository.findByName(dto.getName()).ifPresent(existing -> {
                    if (siteId == null || !existing.getId().equals(siteId)) {
                        throw new InvalidRessourceException("This site already exists!");
                    }
                });

    }


}