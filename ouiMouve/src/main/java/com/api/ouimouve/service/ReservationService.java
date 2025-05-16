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


    public List<VehicleReservationDto> getAllReservationsByVehicle(Long vehicleId) {
        return reservationRepository.findByServiceVehicleId(vehicleId).stream()
                .map(reservationMapper::toVehicleReservationDto)
                .collect(Collectors.toList());
    }

    public List<VehicleReservationDto> getAllReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(reservationMapper::toVehicleReservationDto)
                .collect(Collectors.toList());
    }
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

    public VehicleReservationDto deleteReservation(long id) {
        VehicleReservationDto reservationDto = getReservationById(id);
        if (reservationDto != null) {
            reservationRepository.deleteById(id);
        }
        return reservationDto;
    }

    public List<VehicleReservationDto> getAllReservationsByUserAndFilterByStartDateAndStatus(Long userId, Date start, VehicleStatus status) {
        List<VehicleReservation> reservations = reservationRepository.findByUserWithFilters(userId, start, status);
        return reservations.stream()
                .map(reservationMapper::toVehicleReservationDto)
                .collect(Collectors.toList());
          }

    public VehicleReservationDto updateReservation(long id, VehicleReservationCreateDto reservationDto) {
        Optional<VehicleReservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            reservationRepository.save(reservationMapper.toVehicleReservation(reservationDto));
            return reservationMapper.toVehicleReservationDto(reservationOpt.get());
        }

        throw new RessourceNotFoundException("Reparation not found");
    }
}