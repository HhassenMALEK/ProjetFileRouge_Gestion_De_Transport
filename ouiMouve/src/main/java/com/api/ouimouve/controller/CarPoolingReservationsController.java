package com.api.ouimouve.controller;

import com.api.ouimouve.dto.CarPoolingReservationsCreateDTO;
import com.api.ouimouve.dto.CarPoolingReservationsResponseDTO;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.exception.InvalidRequestException;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.CarPoolingReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carpooling-reservations")
public class CarPoolingReservationsController {
    @Autowired
    private CarPoolingReservationsService carPoolingReservationsService;

    @GetMapping("/list/{userId}")
    public List<CarPoolingReservationsResponseDTO> getAllCarPoolingReservations(@PathVariable Long userId) {
        return carPoolingReservationsService.getAllReservationsByUserId(userId);
    }
    @GetMapping("/{id}")
    public CarPoolingReservationsResponseDTO getCarPoolingReservation(@PathVariable Long id) throws RessourceNotFoundException {
        CarPoolingReservationsResponseDTO reservationsDTO = carPoolingReservationsService.getReservation(id);
        System.out.println("CarPoolingReservationsResponseDTO: " + reservationsDTO);
        return carPoolingReservationsService.getReservation(id);
    }
    @PostMapping()
    public CarPoolingReservationsResponseDTO createCarPoolingReservation(@RequestBody CarPoolingReservationsCreateDTO carPoolingReservations) throws InvalidRessourceException {
        if (carPoolingReservationsService.isTheirAvailableSeats(carPoolingReservations)) {
            throw new InvalidRessourceException("This carpooling is already full. Please choose another one.");
        }
        return carPoolingReservationsService.createReservation(carPoolingReservations);
    }

    /**
     * Cancel a reservation.
     * @param id the ID of the reservation to cancel
     * @param reservation the reservation details
     * @return the updated reservation
     * @throws RessourceNotFoundException if the reservation does not exist
     * @throws InvalidRequestException if the reservation is already cancelled or finished
     */
    @PutMapping("/cancel/{id}")
    public CarPoolingReservationsResponseDTO cancelReservation(@PathVariable Long id, @RequestBody CarPoolingReservationsCreateDTO reservation) throws RessourceNotFoundException, InvalidRequestException {
        Optional<CarPoolingReservationsResponseDTO> dto = Optional.ofNullable(carPoolingReservationsService.getReservation(id));
        if (dto.isEmpty()) {
            throw new RessourceNotFoundException("The requested reservation does not exist, it might have been deleted by the organizer.");
        }
        return switch (dto.get().getStatus()) {
            case FINISHED -> throw new InvalidRequestException("This carpooling is already finished.");
            case CANCELLED -> throw new InvalidRequestException("The reservation is already cancelled.");
            default -> {
                reservation.setStatus(CarPoolingReservationStatus.CANCELLED);
                yield carPoolingReservationsService.updateReservation(id, reservation);
            }
        };
    }

    /**
     * Subscribe to a carpooling reservation.
     * @param id the ID of the reservation
     * @param reservation the reservation details
     * @return the updated reservation
     * @throws RessourceNotFoundException if the reservation does not exist
     */
    @PutMapping("/subscribe/{id}")
    public CarPoolingReservationsResponseDTO subscribeToCarPooling(@PathVariable Long id, @RequestBody CarPoolingReservationsCreateDTO reservation) throws RessourceNotFoundException {
        if (carPoolingReservationsService.isTheirAvailableSeats(reservation)) {
            throw new InvalidRequestException("This carpooling is already full. PLease choose another one.");
        }
        Optional<CarPoolingReservationsResponseDTO> dto = Optional.ofNullable(carPoolingReservationsService.getReservation(id));
        dto.ifPresent(res-> {
            if (res.getCarPooling() == null) {
                throw new InvalidRequestException("The carpooling you're trying to book does not exist.");
            }
            switch (res.getCarPooling().getStatus()) {
                case FINISHED -> throw new InvalidRequestException("This carpooling is already finished.");
                case CANCELLED -> throw new InvalidRequestException("The carpooling has been cancelled by the organizer.");
                case BOOKING_OPEN -> res.setStatus(CarPoolingReservationStatus.BOOKED);
                default -> throw new InvalidRequestException("The carpooling is full.");
            }
        });
        return carPoolingReservationsService.updateReservation(id, reservation);
    }


}
