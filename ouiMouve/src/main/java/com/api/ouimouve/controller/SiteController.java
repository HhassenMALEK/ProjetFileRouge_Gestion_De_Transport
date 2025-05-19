package com.api.ouimouve.controller;

import com.api.ouimouve.dto.SiteCreateDto;
import com.api.ouimouve.dto.SiteResponseDto;
import com.api.ouimouve.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    /**
     * GET /api/sites/list
     * Retourne tous les sites.
     */
    @GetMapping("/list")
    public List<SiteResponseDto> getAllSites() {
        return siteService.getAllSites();
    }

    /**
     * GET /api/sites/{id}
     * Retourne un site par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SiteResponseDto> getSiteById(@PathVariable Long id) {
        SiteResponseDto site = siteService.getSiteById(id);
        return ResponseEntity.ok(site);
    }

    /**
     * POST /api/sites
     * Crée un nouveau site.
     */
    @PostMapping
    public ResponseEntity<SiteResponseDto> createSite(@RequestBody SiteCreateDto dto) {
        SiteResponseDto created = siteService.createSite(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/sites/{id}
     * Met à jour un site existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SiteResponseDto> updateSite(@PathVariable Long id, @RequestBody SiteCreateDto dto) {
        SiteResponseDto updated = siteService.updateSite(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/sites/{id}
     * Supprime un site.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SiteResponseDto> deleteSite(@PathVariable Long id) {
        SiteResponseDto deleted = siteService.deleteSite(id);
        return ResponseEntity.ok(deleted);
    }
}
