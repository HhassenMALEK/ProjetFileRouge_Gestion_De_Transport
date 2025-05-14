package com.api.ouimouve.service;

import com.api.ouimouve.bo.Reparation;
import com.api.ouimouve.bo.VehicleReservation;
import com.api.ouimouve.dto.VehicleReservationDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.VehicleReservationMapper;
import com.api.ouimouve.repository.VehicleReservationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private VehicleReservationRepository reservationRepository;

    @Autowired
    private VehicleReservationMapper reservationMapper;


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

    public VehicleReservationDto createReservation(VehicleReservationDto reservationDto) {
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

    public VehicleReservationDto updateReservation(long id, VehicleReservationDto reservationDto) {
        Optional<VehicleReservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            reservationRepository.save(reservationMapper.toVehicleReservation(reservationDto));
            return reservationMapper.toVehicleReservationDto(reservationOpt.get());
        }

        throw new RessourceNotFoundException("Reparation not found");
    }
}