package com.api.ouimouve.controller;


import com.api.ouimouve.dto.CarPoolingDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.CarPoolingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/carpooling")
public class CarPoolingController {
    @Autowired
    private CarPoolingService carPoolingService;

    /**
     * Fetches all carpoolings from the repository and converts them to DTOs.
     */
    @GetMapping("/list")
    public List<CarPoolingDto> getAllCarpooling() {
        return carPoolingService.getAllCarpooling();
    }

    /**
     * Fetches a carpooling by its ID from the repository and converts it to a DTO.
     */
    @GetMapping("/{id}")
    public CarPoolingDto getCarPoolingById(@PathVariable Long id) {
        CarPoolingDto carPooling = carPoolingService.getCarPoolingById(id);
        if (carPooling == null) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        return carPooling;
    }

    /**
     * Fetches all carpoolings with a specific status from the repository and converts them to DTOs.
     */
    @GetMapping("/status/{status}")
    public List<CarPoolingDto> findByStatus(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarpoolingByStatus(status);
    }

    /**
     * Fetches all carpoolings with departure after the given date from the repository and converts them to DTO*/
    @GetMapping("/departureAfter/{date}")
    public List<CarPoolingDto> getCarPoolingsAfterDate(@PathVariable Date date) {
        return carPoolingService.getCarPoolingsAfterDate(date);
    }

    /**
     * getCarPoolingsByStatusAndDate
     */
    @GetMapping("/status/{status}/date/{date}")
    public List<CarPoolingDto> getCarpoolingByStatus(@PathVariable CarPoolingStatus status, @PathVariable Date date) {
        return carPoolingService.getCarpoolingByStatus(status);
    }

    /**
     * create a carpooling
     *
     * @param carPoolingDto
     * @return CarPoolingDto
     */
    @PostMapping
    public CarPoolingDto createCarPoColing(@RequestBody CarPoolingDto carPoolingDto) {
        return carPoolingService.createCarpooling(carPoolingDto);
    }

    /**
     * update a carpooling
     *
     * @param id
     * @param carPoolingDto
     * @return CarPoolingDto
     */
    @PutMapping("/{id}")
    public CarPoolingDto updateCarPooling(@PathVariable Long id, @RequestBody CarPoolingDto carPoolingDto) {
        CarPoolingDto updated = carPoolingService.updateCarPooling(id, carPoolingDto);
        if (updated == null) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        return updated;
    }

    /**
     * delete a carpooling
     *
     * @param id
     * @return CarPoolingDto
     * ==> a vérifier
     */
    @DeleteMapping("/{id}")
    public CarPoolingDto deleteCarPooling(@PathVariable Long id) {
        CarPoolingDto existing = getCarPoolingById(id);
        if (existing == null) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        carPoolingService.deleteCarpooling(id);
        return existing;
    }
// a vérifier
    @DeleteMapping("/in-progress/{id}")
    public void deleteCarPoolingIfInProgress(@PathVariable Long id) {
        carPoolingService.deleteIfInProgress(id);
    }

    @GetMapping("/status-ordered/{status}")
    public List<CarPoolingDto> getCarpoolingsByStatusOrdered(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarPoolingsByStatusOrdered(status);
    }

    @GetMapping("/overlap/organizer")
    public List<?> findOverlappingForOrganizer(Long userId, Date from, Date to) {
        return carPoolingService.findOverlappingForOrganizer(userId, from, to);
    }

    @GetMapping("/filter")
    public List<?> filterByStatusDateVehicle(Long userId, CarPoolingStatus status, Date departure, Long vehicleId) {
        return carPoolingService.filterByStatusDateVehicle(userId, status, departure, vehicleId);
    }

    @GetMapping("/overlap/vehicle")
    public List<?> findOverlappingForVehicle(Long vehicleId, Date from, Date to) {
        return carPoolingService.findOverlappingForVehicle(vehicleId, from, to);
    }

    @GetMapping("/status-date")
    public List<CarPoolingDto> getByStatusAndDepartureAfter(CarPoolingStatus status, Date date) {
        return carPoolingService.getCarPoolingsByStatusAndDate(status, date);
    }





}

