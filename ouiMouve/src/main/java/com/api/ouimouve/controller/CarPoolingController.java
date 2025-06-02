package com.api.ouimouve.controller;

import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.dto.SiteResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.service.CarPoolingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
/**
 * Controller for managing carpooling operations.
 * Provides endpoints for creating, updating, deleting, and retrieving carpooling information.
 */
@RestController
@RequestMapping("/api/carpooling")
public class CarPoolingController {

    @Autowired
    private CarPoolingService carPoolingService;

    /**
     * Creates a new carpooling entry.
     * @param dto containing carpooling details.
     * @return the created carpooling entry.
     */
    @PostMapping
    public CarPoolingResponseDto createCarPooling(@RequestBody CarPoolingCreateDto dto) {
        return carPoolingService.createCarpooling(dto);
    }

    /**
     * Updates an existing carpooling entry.
     * @param id the ID of the carpooling to update.
     * @param dto the updated carpooling details.
     * @return the updated carpooling entry.
     */
    @PatchMapping("/{id}")
    public CarPoolingResponseDto updateCarPooling(@PathVariable Long id, @RequestBody CarPoolingCreateDto dto) {
        return carPoolingService.updateCarPooling(id, dto);
    }

    /**
     * Deletes a carpooling entry by its ID.
     * @param id the ID of the carpooling entry.
     * @return a response indicating the deletion status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarPooling(@PathVariable Long id) {
        carPoolingService.deleteCarpooling(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Retrieves all carpoolings.
     * @return list of all carpoolings.
     */
    @GetMapping("/list")
    public List<CarPoolingResponseDto> getAll() {
        return carPoolingService.getAllCarpooling();
    }

    /**
     * Retrieves a carpooling entry by its ID.
     * @param id the ID of the carpooling entry.
     * @return the carpooling entry with the specified ID.
     */
    @GetMapping("/{id}")
    public CarPoolingResponseDto getById(@PathVariable Long id) {
        return carPoolingService.getCarPoolingById(id);
    }

    /**
     * Filters carpoolings based on optional criteria such as organizer ID, status, departure date, and vehicle ID.
     * All parameters are optional; if none are provided, all carpoolings are returned.
     *
     * @param organizerId optional ID of the organizer (user)
     * @param status optional status of the carpooling (e.g., PENDING, VALIDATED)
     * @param startDate optional departure date to filter
     * @param siteDeparture optional departure site
     * @param siteDestination optional destination site
     * @param vehicleId optional ID of the vehicle
     * @return list of carpoolings matching the provided filters
     */
    @GetMapping("/filter")
    public List<CarPoolingResponseDto> filterByCriteria(
            @RequestParam(required = false) Long organizerId,
            @RequestParam(required = false) CarPoolingStatus status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) SiteResponseDto siteDeparture,
            @RequestParam(required = false) SiteResponseDto siteDestination,
            @RequestParam(required = false) Long vehicleId
    ) {
        return carPoolingService.getCarPoolingByFilter(organizerId, status, startDate, siteDeparture, siteDestination, vehicleId);
    }
}
