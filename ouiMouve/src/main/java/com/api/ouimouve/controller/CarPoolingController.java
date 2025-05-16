package com.api.ouimouve.controller;

import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.service.CarPoolingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/carpooling")
public class CarPoolingController {

    @Autowired
    private CarPoolingService carPoolingService;

    @PostMapping
    public CarPoolingResponseDto createCarPooling(@RequestBody CarPoolingCreateDto dto) {
        return carPoolingService.createCarpooling(dto);
    }

    @PatchMapping("/{id}")
    public CarPoolingResponseDto updateCarPooling(@PathVariable Long id, @RequestBody CarPoolingCreateDto dto) {
        return carPoolingService.updateCarPooling(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarPooling(@PathVariable Long id) {
        carPoolingService.deleteCarpooling(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public List<CarPoolingResponseDto> getByStatus(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarpoolingByStatus(status);
    }

    @GetMapping("/status/{status}/after/{date}")
    public List<CarPoolingResponseDto> getByStatusAndDate(@PathVariable CarPoolingStatus status, @PathVariable Date date) {
        return carPoolingService.getCarPoolingsByStatusAndDate(status, date);
    }

    @GetMapping("/departure-after/{date}")
    public List<CarPoolingResponseDto> getByDepartureAfter(@PathVariable Date date) {
        return carPoolingService.getCarPoolingsAfterDate(date);
    }

    @GetMapping("/status-ordered/{status}")
    public List<CarPoolingResponseDto> getCarpoolingsByStatusOrdered(@PathVariable CarPoolingStatus status) {
        return carPoolingService.getCarPoolingsByStatusOrdered(status);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<CarPoolingResponseDto> getByVehicle(@PathVariable Long vehicleId) {
        return carPoolingService.findByVehicleId(vehicleId);
    }

    @GetMapping("/organizer/{organizerId}")
    public List<CarPoolingResponseDto> getByOrganizer(@PathVariable Long organizerId) {
        return carPoolingService.findByOrganizerId(organizerId);
    }

    @GetMapping("/filter")
    public List<CarPoolingResponseDto> filterByCriteria(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) CarPoolingStatus status,
            @RequestParam(required = false) Date departure,
            @RequestParam(required = false) Long vehicleId
    ) {
        return carPoolingService.filterByStatusDateVehicle(userId, status, departure, vehicleId);
    }
}
