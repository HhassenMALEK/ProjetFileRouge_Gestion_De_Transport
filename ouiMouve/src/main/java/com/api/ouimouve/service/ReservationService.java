package com.api.ouimouve.service;

import com.api.ouimouve.bo.Reparation;
import com.api.ouimouve.bo.VehicleReservation;
import com.api.ouimouve.dto.VehicleReservationCreateDto;
import com.api.ouimouve.dto.VehicleReservationDto;
import com.api.ouimouve.enumeration.VehicleStatus;
import com.api.ouimouve.exception.ReservationConflictException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.VehicleReservationMapper;
import com.api.ouimouve.repository.ReparationRepository;
import com.api.ouimouve.repository.VehicleReservationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private VehicleReservationRepository reservationRepository;

    @Autowired
    private VehicleReservationMapper reservationMapper;

    @Autowired
    private ReparationRepository reparationRepository;


    /**
     * Get all reservations for a specific vehicle.
     * @param vehicleId
     * @return
     */
    public List<VehicleReservationDto> getAllReservationsByVehicle(Long vehicleId) {
        return reservationRepository.findByServiceVehicleId(vehicleId).stream()
                .map(reservationMapper::toVehicleReservationDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all reservations for a specific user.
     * @param userId
     * @return
     */
    public List<VehicleReservationDto> getAllReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(reservationMapper::toVehicleReservationDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a reservation by its ID.
     * @param id
     * @return
     */
    public VehicleReservationDto getReservationById(long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toVehicleReservationDto)
                .orElse(null);
    }

   /**
    * Create a new vehicle reservation after checking for availability.
    * Throws ReservationConflictException if the vehicle is already booked for the requested period
    * or if there's a repair scheduled during that period.
    *
    * @param reservationDto the reservation data
    * @return the created reservation
    * @throws ReservationConflictException if there is a reservation conflict or repair conflict
    */


   public VehicleReservationDto createReservation(VehicleReservationCreateDto reservationDto) {
       // Check for overlapping reservations
       List<VehicleReservation> overlappingReservations = reservationRepository.findOverlappingReservations(
               reservationDto.getServiceVehicleId(),
               reservationDto.getStart(),
               reservationDto.getEnd());

       if (!overlappingReservations.isEmpty()) {
           throw new ReservationConflictException("Vehicle already booked for this period");
       }

       // Check for overlapping repairs
       List<Reparation> overlappingReparations = reparationRepository.findOverlappingReparations(
               reservationDto.getServiceVehicleId(),
               reservationDto.getStart(),
               reservationDto.getEnd());

       if (!overlappingReparations.isEmpty()) {
           throw new ReservationConflictException("Vehicle scheduled for repair during this period");
       }

       // If no overlap, create the reservation
       return reservationMapper.toVehicleReservationDto(
               reservationRepository.save(reservationMapper.toVehicleReservation(reservationDto)));
   }

    /**
     * Delete a reservation by its ID.
     * @param id
     * @return
     */
    public VehicleReservationDto deleteReservation(long id) {
        VehicleReservationDto reservationDto = getReservationById(id);
        if (reservationDto != null) {
            reservationRepository.deleteById(id);
        }
        return reservationDto;
    }

    /**
     * Get all reservations for a specific user, filtered by start date and status.
     * @param userId
     * @param start
     * @param status
     * @return
     */
    public List<VehicleReservationDto> getAllReservationsByUserAndFilterByStartDateAndStatus(Long userId, Date start, VehicleStatus status) {
        List<VehicleReservation> reservations = reservationRepository.findByUserWithFilters(userId, start, status);
        return reservations.stream()
                .map(reservationMapper::toVehicleReservationDto)
                .collect(Collectors.toList());
          }

    /**
     * Update an existing vehicle reservation after checking for availability.
     * This method verifies that no other reservations or repairs overlap with the new period.
     * The original reservation being updated is excluded from overlap checks.
     *
     * @param id the ID of the reservation to update
     * @param reservationDto the updated reservation data
     * @return the updated reservation DTO
     * @throws ReservationConflictException if there is a reservation or repair conflict
     * @throws RessourceNotFoundException if the reservation is not found
     */
    public VehicleReservationDto updateReservation(long id, VehicleReservationCreateDto reservationDto) {
        // Check if the reservation exists
        Optional<VehicleReservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isEmpty()) {
            throw new RessourceNotFoundException("Reservation not found");
        }

        // Find any reservations that overlap with the requested period
        List<VehicleReservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                reservationDto.getServiceVehicleId(),
                reservationDto.getStart(),
                reservationDto.getEnd());

        // Exclude the current reservation from the overlap check
        overlappingReservations = overlappingReservations.stream()
                .filter(reservation -> reservation.getId() != id)
                .toList();

        // Check if there are any other overlapping reservations
        if (!overlappingReservations.isEmpty()) {
            throw new ReservationConflictException("Vehicle already booked for this period");
        }

        // Check for overlapping repairs
        List<Reparation> overlappingReparations = reparationRepository.findOverlappingReparations(
                reservationDto.getServiceVehicleId(),
                reservationDto.getStart(),
                reservationDto.getEnd());

        // Check if there are any overlapping repairs
        if (!overlappingReparations.isEmpty()) {
            throw new ReservationConflictException("Vehicle scheduled for repair during this period");
        }

        // Create an updated reservation with the existing ID
        VehicleReservation updatedReservation = reservationMapper.toVehicleReservation(reservationDto);
        updatedReservation.setId(id);

        // Save and return the updated reservation
        return reservationMapper.toVehicleReservationDto(reservationRepository.save(updatedReservation));
    }
}