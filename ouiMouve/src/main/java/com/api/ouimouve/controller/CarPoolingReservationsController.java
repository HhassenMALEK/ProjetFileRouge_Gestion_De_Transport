package com.api.ouimouve.controller;

import com.api.ouimouve.dto.CarPoolingReservationsDTO;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.CarPoolingReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/carpooling-reservations")
public class CarPoolingReservationsController {
    @Autowired
    private CarPoolingReservationsService carPoolingReservationsService;

    @GetMapping("/list")
    public List<CarPoolingReservationsDTO> getAllCarPoolingReservations() {
        return carPoolingReservationsService.getAllReservations();
    }

    @GetMapping("/list/{userId}")
    public List<CarPoolingReservationsDTO> getAllCarPoolingReservationsByUserId(@PathVariable Long userId) {
        return carPoolingReservationsService.getAllReservationsByUserId(userId);
    }
    @GetMapping("/{id}")
    public CarPoolingReservationsDTO getCarPoolingReservation(@PathVariable Long id) throws RessourceNotFoundException {
        return carPoolingReservationsService.getReservation(id);
    }
    @PostMapping()
    public CarPoolingReservationsDTO createCarPoolingReservation(@RequestBody CarPoolingReservationsDTO carPoolingReservations) throws InvalidRessourceException {
        return carPoolingReservationsService.createReservation(carPoolingReservations);
    }
    @DeleteMapping("/{id}")
    public void deleteCarPoolingReservation(@PathVariable Long id) throws RessourceNotFoundException {
        carPoolingReservationsService.deleteReservation(id);
    }
    @PutMapping("/{id}")
    public CarPoolingReservationsDTO updateCarPoolingReservation(@PathVariable Long id, @RequestBody CarPoolingReservationsDTO carPoolingReservations) throws RessourceNotFoundException {
        return carPoolingReservationsService.updateReservation(id, carPoolingReservations);
    }
}
