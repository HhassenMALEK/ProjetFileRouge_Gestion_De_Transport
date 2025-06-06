package com.api.ouimouve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.service.CarPoolingService;

/**
 * Controller for managing carpooling operations.
 * Provides endpoints for creating, updating, deleting, and retrieving
 * carpooling information.
 */
@RestController
@RequestMapping("/api/carpooling")
public class CarPoolingController {

    @Autowired
    private CarPoolingService carPoolingService;

    /**
     * Creates a new carpooling entry.
     * 
     * @param dto containing carpooling details.
     * @return the created carpooling entry.
     */
    @PostMapping
    public CarPoolingResponseDto createCarPooling(@RequestBody CarPoolingCreateDto dto) {
        return carPoolingService.createCarpooling(dto);
    }

    /**
     * Updates an existing carpooling entry.
     * 
     * @param id  the ID of the carpooling to update.
     * @param dto the updated carpooling details.
     * @return the updated carpooling entry.
     */
    @PatchMapping("/{id}")
    public CarPoolingResponseDto updateCarPooling(@PathVariable Long id, @RequestBody CarPoolingCreateDto dto) {
        return carPoolingService.updateCarPooling(id, dto);
    }

    /**
     * Deletes a carpooling entry by its ID.
     * 
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
     * 
     * @return list of all carpoolings.
     */
    @GetMapping("/list")
    public List<CarPoolingResponseDto> getAll() {
        return carPoolingService.getAllCarpooling();
    }

    /**
     * Retrieves a carpooling entry by its ID.
     * 
     * @param id the ID of the carpooling entry.
     * @return the carpooling entry with the specified ID.
     */
    @GetMapping("/{id}")
    public CarPoolingResponseDto getById(@PathVariable Long id) {
        return carPoolingService.getCarPoolingById(id);
    }

    /**
     * Filters carpoolings based on optional criteria such as organizer ID, status,
     * departure date, and vehicle ID.
     * All parameters are optional; if none are provided, all carpoolings are
     * returned.
     *
     * @param organizerId     optional ID of the organizer (user)
     * @param status          optional status of the carpooling (e.g., PENDING,
     *                        VALIDATED)
     * @param startDate       optional departure date to filter
     * @param endDate         option optional departure site
     * @param nameDestination optional destination site
     * @param vehicleId       optional ID of the vehicle
     * @return list of carpoolings matching the provided filters
     * @return capacity passagers
     */
    @GetMapping("/filter")
    public List<CarPoolingResponseDto> filterByCriteria(
            @RequestParam(required = false) Long organizerId,
            @RequestParam(required = false) CarPoolingStatus status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String nameDeparture,
            @RequestParam(required = false) String nameDestination,
            @RequestParam(required = false) Long vehicleId,
            @RequestParam(required = false) Integer capacity

    ) {
        return carPoolingService.getCarPoolingByFilter(organizerId, status, startDate, endDate, nameDeparture,
                nameDestination, vehicleId, capacity);
    }
}
