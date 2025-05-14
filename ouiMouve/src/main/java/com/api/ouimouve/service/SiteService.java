package com.api.ouimouve.service;


import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.ReparationDto;
import com.api.ouimouve.dto.SiteDto;
import com.api.ouimouve.mapper.SiteMapper;
import com.api.ouimouve.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private SiteMapper siteMapper;

    /**
     * Fetches all sites from the repository and converts them to DTOs.
     * Get all sites from the repository and convert them to DTOs.
     */
    public List<SiteDto> getAllSites() {
        return siteRepository.findAll().stream()
                .map(siteMapper::toSiteDto)
                .collect(Collectors.toList());
    }
/**
     * Fetches a site by its ID from the repository and converts it to a DTO.
     *
     * @param id the ID of the site to fetch
     * @return the SiteDto object if found, null otherwise
     */
    public SiteDto getSiteById(Long id) {
        return siteRepository.findById(id)
                .map(siteMapper::toSiteDto)
                .orElse(null);
    }

    /**
     * Creates a new site in the repository and converts it to a DTO.
     * @param siteDto
     * @return the created SiteDto object
     */
    public SiteDto createSite(SiteDto siteDto) {
        return siteMapper.toSiteDto(
                siteRepository.save(siteMapper.toSite(siteDto))
        );
    }

    /**
     * Updates an existing site in the repository and converts it to a DTO.
     * @param id of the site to update
     * @param siteDto of the site to update
     * @return the updated SiteDto object
     */
    public SiteDto updateSite(long id, SiteDto siteDto) {
        return siteRepository.findById(id)
                .map(site -> {
                    site.setName(siteDto.getName());
                    // Tu peux gérer ici address ou vehicle si besoin
                    return siteMapper.toSiteDto(siteRepository.save(site));
                })
                .orElseThrow(() -> new RuntimeException("Site not found"));
    }




    /**
     * delete a site by its ID from the repository and converts it to a DTO.
     * @param id of the site to delete
     * @return
     */
    public SiteDto deleteSite(Long id) {
        SiteDto dto = getSiteById(id);
        if (dto != null) {
            siteRepository.deleteById(id);
        }
        return dto;
    }
}