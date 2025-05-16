package com.api.ouimouve.controller;


import com.api.ouimouve.bo.Vehicle;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.CarPoolingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/carpooling")
public class CarPoolingController {
    @Autowired
    private CarPoolingService carPoolingService;




//    /**
//     * Fetches all carpoolings with a specific status from the repository and converts them to DTOs.
//     */
//    @GetMapping("/{status}/filter")
//    public List<CarPoolingResponseDto> getCarpoolingByStatus(@PathVariable CarPoolingStatus status) {
//        return carPoolingService.getCarpoolingByStatus(status);
//    }
//    @GetMapping("/{vceicule}/filter")
//    public List<CarPoolingResponseDto> getCarPoolingsByStatusAndDate(@PathVariable  CarPoolingStatus status, Date date) {
//        return carPoolingService.getCarpoolingByStatus(status,date);
//    }
//
//    @GetMapping("/{date}/filter")
//    public List<CarPoolingResponseDto> findByStatus(@PathVariable Date date) {
//        return carPoolingService.getCarpoolingByStatus(date);
//    }


    /**
     * Fetches all carpoolings with departure after the given date from the repository and converts them to DTO*/
    @GetMapping("/departureAfter/{date}")
    public List<CarPoolingResponseDto> getCarPoolingsAfterDate(@PathVariable Date date) {
        return carPoolingService.getCarPoolingsAfterDate(date);
    }

    /**
     * getCarPoolingsByStatusAndDate
     */
    @GetMapping("/status/{status}/date/{date}")
    public List<CarPoolingResponseDto> getCarpoolingByStatus(@PathVariable CarPoolingStatus status, @PathVariable Date date) {
        return carPoolingService.getCarpoolingByStatus(status);
    }

    /**
     * create a carpooling
     *
     * @param carPoolingCreateDto
     * @return CarPoolingResponseDto
     */
    @PostMapping
    public CarPoolingResponseDto createCarPoColing(@RequestBody CarPoolingCreateDto carPoolingCreateDto) {
        return carPoolingService.createCarpooling(carPoolingCreateDto);
    }

    /**
     * update a carpooling
     *
     * @param id
     * @param carPoolingCreateDto
     * @return CarPoolingResponseDto
     */

    /**
     * delete a carpooling
     *
     * @param id
     * @return CarPoolingResponseDto
     * ==> a vérifier
     */
    @DeleteMapping("/{id}")
    public CarPoolingResponseDto deleteCarPooling(@PathVariable Long id) {
        CarPoolingResponseDto existing = getCarPoolingById(id);
        if (existing == null) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        carPoolingService.deleteCarpooling(id);
        return existing;
    }

    @GetMapping("/status-ordered/{status}")
    public List<CarPoolingResponseDto> getCarpoolingsByStatusOrdered(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarPoolingsByStatusOrdered(status);
    }

    @GetMapping("/overlap/organizer")
    public List<CarPoolingResponseDto> findOverlappingForOrganizer(Long userId, Date from, Date to) {
        return carPoolingService.findOverlappingForOrganizer(userId, from, to);
    }

    @GetMapping("/filter")
    public List<CarPoolingResponseDto> filterByStatusDateVehicle(Long userId, CarPoolingStatus status, Date departure, Long vehicleId) {
        return carPoolingService.filterByStatusDateVehicle(userId, status, departure, vehicleId);
    }

    @GetMapping("/overlap/vehicle")
    public List<CarPoolingResponseDto> findOverlappingForVehicle(Long vehicleId, Date from, Date to) {
        return carPoolingService.findOverlappingForVehicle(vehicleId, from, to);
    }

    @GetMapping("/status-date")
    public List<CarPoolingResponseDto> getByStatusAndDepartureAfter(CarPoolingStatus status, Date date) {
        return carPoolingService.getCarPoolingsByStatusAndDate(status, date);
    }









    /**
     * Fetches all carpoolings from the repository and converts them to DTOs.
     */
    @GetMapping("/list")
    public List<CarPoolingResponseDto> getAllCarpooling() {
        return carPoolingService.getAllCarpooling();
    }

    /**
     * Fetches a carpooling by its ID from the repository and converts it to a DTO.
     */
    @GetMapping("/{id}")
    public CarPoolingResponseDto getCarPoolingById(@PathVariable Long id) {
        CarPoolingResponseDto carPooling = carPoolingService.getCarPoolingById(id);
        if (carPooling == null) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        return carPooling;
    }



    @Operation(summary = "Update a reservation by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Carpooling updated"),
            @ApiResponse(responseCode = "403", description = "access required"),
            @ApiResponse(responseCode = "404", description = "No carpooling found"),
            @ApiResponse(responseCode = "500", description = "Internal Serveur Error") })
    @PatchMapping("/{id}")
    public CarPoolingResponseDto updateCarPooling(@PathVariable Long id, @RequestBody CarPoolingCreateDto carPoolingCreateDto) throws RessourceNotFoundException{
        CarPoolingResponseDto updated = carPoolingService.updateCarPooling(id, carPoolingCreateDto);
        if (updated == null) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        return updated;
    }


}

