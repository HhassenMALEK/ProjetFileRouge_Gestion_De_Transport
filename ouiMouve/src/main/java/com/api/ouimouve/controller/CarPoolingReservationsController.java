package com.api.ouimouve.controller;

import com.api.ouimouve.dto.CarPoolingReservationsCreateDTO;
import com.api.ouimouve.dto.CarPoolingReservationsResponseDTO;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.exception.InvalidRequestException;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.CarPoolingReservationsService;
import com.api.ouimouve.utils.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carpooling-reservations")
public class CarPoolingReservationsController {
    @Autowired
    private CarPoolingReservationsService carPoolingReservationsService;
    @Autowired
    private AuthContext authContext;

    @GetMapping("/list")
    public List<CarPoolingReservationsResponseDTO> getAllCarPoolingReservations() {
        return carPoolingReservationsService.getAllReservationsByUserId(authContext.getCurrentUser().getId());
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
        carPoolingReservations.setStatus(CarPoolingReservationStatus.BOOKED);
        carPoolingReservations.setUserId(authContext.getCurrentUser().getId());
        // The Date is set to the current date and time
        carPoolingReservations.setDate(Date.from(Instant.now()));
        CarPoolingReservationsResponseDTO res =  carPoolingReservationsService.createReservation(carPoolingReservations);
        // Set the status to BOOKED after the creation of the reservation
        res.setStatus(CarPoolingReservationStatus.BOOKED);
        return res;
    }

    /**
     * Cancel a reservation.
     * @param resId the ID of the reservation to cancel
     * @return the updated reservation
     * @throws RessourceNotFoundException if the reservation does not exist
     * @throws InvalidRequestException if the reservation is already cancelled or finished
     */
    @PutMapping("/cancel/{resId}")
    public CarPoolingReservationsResponseDTO cancelReservation(@PathVariable Long resId) throws RessourceNotFoundException, InvalidRequestException {
        Optional<CarPoolingReservationsResponseDTO> reservation = Optional.ofNullable(carPoolingReservationsService.getReservation(resId));
                           //        // Check if the reservation exists
        if (reservation.isEmpty()) {
            throw new RessourceNotFoundException("The requested reservation does not exist, it might have been deleted by the organizer.");
        }
        // Check if the reservation is already cancelled or finished else update the status to CANCELLED.
        return switch (reservation.get().getStatus()) {
            case FINISHED -> throw new InvalidRequestException("This carpooling is already finished.");
            case CANCELLED -> throw new InvalidRequestException("The reservation is already cancelled.");
            default -> carPoolingReservationsService.updateReservation(resId, CarPoolingReservationStatus.CANCELLED);
        };
    }

    /**
     * Subscribe to a carpooling reservation.
     * @param resId the ID of the reservation
     * @return the updated reservation
     * @throws RessourceNotFoundException if the reservation does not exist
     */
    @PutMapping("/subscribe/{resId}")
    public CarPoolingReservationsResponseDTO subscribeToCarPooling(@PathVariable Long resId) throws RessourceNotFoundException {
        Optional<CarPoolingReservationsResponseDTO> reservation = Optional.ofNullable(carPoolingReservationsService.getReservation(resId));
        // Check if the reservation as available seats
        reservation.ifPresent(res-> {
            if (res.getStatus() == CarPoolingReservationStatus.BOOKED) {
                throw new InvalidRequestException("You already booked this carpooling.");
            }
            if (carPoolingReservationsService.noAvailableSeats(res)) {
                throw new InvalidRequestException("This carpooling is already full. PLease choose another one.");
            }// If no carpooling is found, throw an exception
            if (res.getCarPooling() == null) {
                throw new InvalidRequestException("The carpooling you're trying to book does not exist.");
            }
            // Check the status of the carpooling, if it's finished or cancelled, throw an exception
            switch (res.getCarPooling().getStatus()) {
                case FINISHED -> throw new InvalidRequestException("This carpooling is already finished.");
                case CANCELLED -> throw new InvalidRequestException("The carpooling has been cancelled by the organizer.");
                // If the carpooling is open, set the status to BOOKED on the user reservation
                case BOOKING_FULL -> throw new InvalidRequestException("The carpooling is full. PLease choose another one.");
                // Default equals to a full carpooling
                default -> carPoolingReservationsService.updateReservation(resId, CarPoolingReservationStatus.BOOKED);
            }
        });
        throw new RessourceNotFoundException("The requested reservation does not exist.");
    }


}
