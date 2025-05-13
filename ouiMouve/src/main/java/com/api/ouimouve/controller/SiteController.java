package com.api.ouimouve.controller;

import com.api.ouimouve.dto.SiteDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/site")
public class SiteController {
    @Autowired
    private SiteService siteService;

    /**
     * Fetches all sites from the repository and converts them to DTOs.
     */
    @GetMapping("/list")
    public List<SiteDto> getAllSites() {
        return siteService.getAllSites();
    }

    /**
     * Fetches a site by its ID from the repository and converts it to a DTO
     */
    @GetMapping("/{id}")
    public SiteDto getSiteById(@PathVariable Long id) {
        SiteDto site = siteService.getSiteById(id);
        if (site == null) {
            throw new RessourceNotFoundException("Site not found with id: " + id);
        }
        return site;
    }

    /**
     * Creates a new site in the repository and converts it to a DTO.
     */
    @PostMapping
    public ResponseEntity<SiteDto> createSite(@RequestBody SiteDto siteDto) {
        SiteDto created = siteService.createSite(siteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing site in the repository and converts it to a DTO.
     */
    @PutMapping("/{id}")
    public SiteDto updateSite(@PathVariable Long id, @RequestBody SiteDto siteDto) {
        return siteService.updateSite(id, siteDto);
    }

    /**
     * Deletes a site by its ID from the repository and returns the deleted DTO.
     */
    @DeleteMapping("/{id}")
    public SiteDto deleteSite(@PathVariable Long id) {
        SiteDto deleted = siteService.deleteSite(id);
        if (deleted == null) {
            throw new RessourceNotFoundException("Site not found with id: " + id);
        }
        return deleted;
    }

}
