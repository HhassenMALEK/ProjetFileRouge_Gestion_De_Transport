package com.api.ouimouve.controller;


import com.api.ouimouve.bo.CarPoolingStatus;
import com.api.ouimouve.dto.CarPoolingDto;
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
    public List<CarPoolingDto> getAllCarPoolings() {
        return carPoolingService.getAllCarPoolings();
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
    public List<CarPoolingDto> getCarPoolingsByStatus(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarPoolingsByStatus(status);
    }

    /**
     * Fetches all carpoolings with departure after the given date from the repository and converts them to DTOs.
     */
    @GetMapping("/departureAfter/{date}")
    public List<CarPoolingDto> getCarPoolingsAfterDate(@PathVariable Date date) {
        return carPoolingService.getCarPoolingsAfterDate(date);
    }

    /**
     * getCarPoolingsByStatusAndDate
     */
    @GetMapping("/status/{status}/date/{date}")
    public List<CarPoolingDto> getCarPoolingsByStatusAndDate(@PathVariable CarPoolingStatus status, @PathVariable Date date) {
        return carPoolingService.getCarPoolingsByStatusAndDate(status, date);
    }

    /**
     * create a carpooling
     *
     * @param carPoolingDto
     * @return CarPoolingDto
     */
    @PostMapping
    public CarPoolingDto createCarPooling(@RequestBody CarPoolingDto carPoolingDto) {
        return carPoolingService.createCarPooling(carPoolingDto);
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
     */
    @DeleteMapping("/{id}")
    public CarPoolingDto deleteCarPooling(@PathVariable Long id) {
        CarPoolingDto existing = getCarPoolingById(id);
        if (existing == null) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        carPoolingService.deleteCarPooling(id);
        return existing;
    }


}

