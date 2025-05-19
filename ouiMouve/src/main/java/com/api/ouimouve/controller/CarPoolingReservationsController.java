package com.api.ouimouve.controller;

import com.api.ouimouve.dto.CarPoolingReservationsCreateDTO;
import com.api.ouimouve.dto.CarPoolingReservationsResponseDTO;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.exception.EmptyListException;
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
    public List<CarPoolingReservationsResponseDTO> getAllCarPoolingReservations(@PathVariable Long userId) throws EmptyListException {
        List<CarPoolingReservationsResponseDTO> reservations = carPoolingReservationsService.getAllReservationsByUserId(userId);
        if (reservations.isEmpty()) {
            throw new EmptyListException("You currently have no reservations.");
        } else {
            return reservations;
        }
    }
    @GetMapping("/{id}")
    public CarPoolingReservationsResponseDTO getCarPoolingReservation(@PathVariable Long id) throws RessourceNotFoundException {
        CarPoolingReservationsResponseDTO reservationsDTO = carPoolingReservationsService.getReservation(id);
        System.out.println("CarPoolingReservationsResponseDTO: " + reservationsDTO);
        return carPoolingReservationsService.getReservation(id);
    }
    @PostMapping()
    public CarPoolingReservationsResponseDTO createCarPoolingReservation(@RequestBody CarPoolingReservationsCreateDTO carPoolingReservations) throws InvalidRessourceException {
        if (carPoolingReservationsService.noAvailableSeats(carPoolingReservations)) {
            throw new InvalidRessourceException("This carpooling is already full. Please choose another one.");
        }
        CarPoolingReservationsResponseDTO res =  carPoolingReservationsService.createReservation(carPoolingReservations);
        // Set the status to BOOKED after the creation of the reservation
        res.setStatus(CarPoolingReservationStatus.BOOKED);
        return res;
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
        // Check if the reservation exists
        if (dto.isEmpty()) {
            throw new RessourceNotFoundException("The requested reservation does not exist, it might have been deleted by the organizer.");
        }
        // Check if the reservation is already cancelled or finished else update the status to CANCELLED.
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
        // Check if the reservation as available seats
        if (carPoolingReservationsService.noAvailableSeats(reservation)) {
            throw new InvalidRequestException("This carpooling is already full. PLease choose another one.");
        }
        Optional<CarPoolingReservationsResponseDTO> dto = Optional.ofNullable(carPoolingReservationsService.getReservation(id));
        // Check if the reservation exists
        dto.ifPresent(res-> {
            // If no carpooling is found, throw an exception
            if (res.getCarPooling() == null) {
                throw new InvalidRequestException("The carpooling you're trying to book does not exist.");
            }
            // Check the status of the carpooling, if it's finished or cancelled, throw an exception
            switch (res.getCarPooling().getStatus()) {
                case FINISHED -> throw new InvalidRequestException("This carpooling is already finished.");
                case CANCELLED -> throw new InvalidRequestException("The carpooling has been cancelled by the organizer.");
                // If the carpooling is open, set the status to BOOKED on the user reservation
                case BOOKING_OPEN -> res.setStatus(CarPoolingReservationStatus.BOOKED);
                // Default equals to a full carpooling
                default -> throw new InvalidRequestException("The carpooling is full.");
            }
        });
        // Update the reservation accordingly, the status will be set to BOOKED
        return carPoolingReservationsService.updateReservation(id, reservation);
    }


}
