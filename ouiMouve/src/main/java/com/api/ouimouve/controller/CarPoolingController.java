package com.api.ouimouve.controller;

import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
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
     * Retrieves carpoolings by their status.
     * @param status the status to filter by.
     * @return list of carpoolings with the specified status.
     */
    @GetMapping("/status/{status}")
    public List<CarPoolingResponseDto> getByStatus(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarpoolingByStatus(status);
    }

    /**
     * Retrieves carpoolings by status and with departure after a given date.
     * @param status the status to filter by.
     * @param date the date to filter from.
     * @return list of matching carpoolings.
     */
    @GetMapping("/status/{status}/after/{date}")
    public List<CarPoolingResponseDto> getByStatusAndDate(@PathVariable CarPoolingStatus status, @PathVariable Date date) {
        return carPoolingService.getCarPoolingsByStatusAndDate(status, date);
    }

    /**
     * Retrieves carpoolings scheduled after a given date.
     * @param date the date to filter from.
     * @return list of future carpoolings.
     */
    @GetMapping("/departure-after/{date}")
    public List<CarPoolingResponseDto> getByDepartureAfter(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date) {
        return carPoolingService.getCarPoolingsAfterDate(date);
    }

    /**
     * Retrieves carpoolings by status, ordered by departure time.
     * @param status the status to filter by.
     * @return ordered list of carpoolings with the specified status.
     */
    @GetMapping("/status-ordered/{status}")
    public List<CarPoolingResponseDto> getCarpoolingsByStatusOrdered(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarPoolingsByStatusOrdered(status);
    }

    /**
     * Retrieves carpoolings by vehicle ID.
     * @param vehicleId the ID of the vehicle.
     * @return list of carpoolings using the specified vehicle.
     */
    @GetMapping("/vehicle/{vehicleId}")
    public List<CarPoolingResponseDto> getByVehicle(@PathVariable Long vehicleId) {
        return carPoolingService.findByVehicleId(vehicleId);
    }

    /**
     * Retrieves carpoolings organized by a specific user.
     * @param organizerId the ID of the organizer.
     * @return list of carpoolings organized by the specified user.
     */
    @GetMapping("/organizer/{organizerId}")
    public List<CarPoolingResponseDto> getByOrganizer(@PathVariable Long organizerId) {
        return carPoolingService.findByOrganizerId(organizerId);
    }

    /**
     * Filters carpoolings based on optional criteria such as organizer ID, status, departure date, and vehicle ID.
     * All parameters are optional; if none are provided, all carpoolings are returned.
     *
     * @param organizerId optional ID of the organizer (user)
     * @param status optional status of the carpooling (e.g., PENDING, VALIDATED)
     * @param startDate optional departure date to filter
     * @param endDate   optional arrival date to filter
     * @param vehicleId optional ID of the vehicle
     * @return list of carpoolings matching the provided filters
     */
    @GetMapping("/filter")
    public List<CarPoolingResponseDto> filterByCriteria(
            @RequestParam(required = false) Long organizerId,
            @RequestParam(required = false) CarPoolingStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date endDate,
            @RequestParam(required = false) Long vehicleId
    ) {
        return carPoolingService.filterByStatusDateVehicle(organizerId, status, startDate, endDate, vehicleId);
    }
}
