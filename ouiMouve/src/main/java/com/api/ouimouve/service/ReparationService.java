package com.api.ouimouve.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ouimouve.bo.Reparation;
import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.bo.Vehicle;
import com.api.ouimouve.dto.ReparationCreateDto;
import com.api.ouimouve.dto.ReparationResponseDto;
import com.api.ouimouve.exception.ReservationConflictException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.ReparationMapper;
import com.api.ouimouve.repository.ReparationRepository;
import com.api.ouimouve.repository.ServiceVehicleRepository;
import com.api.ouimouve.repository.VehicleRepository;

@Service
public class ReparationService {
    @Autowired
    private ReparationRepository reparationRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ReparationMapper reparationMapper;
    @Autowired
    private ServiceVehicleRepository serviceVehicleRepository;

    // Add methods to handle CRUD operations for Reparation entities
    /**
     * Get all Reparations
     * 
     * @return a list of ReparationDto
     */
    public List<ReparationResponseDto> getAllReparations(Long vehicleId) {
        return reparationRepository.findByServiceVehicleId(vehicleId).stream()
                .map(reparationMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a Reparation by its ID
     * 
     * @param id the ID of the Reparation
     * @return the ReparationDto if found, null otherwise
     */
    public ReparationResponseDto getReparationById(long id) {
        return reparationRepository.findById(id)
                .map(reparationMapper::toDto)
                .orElse(null);
    }

    /**
     * Create a new Reparation
     * 
     * @param reparationDto the ReparationDto to create
     * @return the created ReparationDto
     */
    public ReparationResponseDto createReparation(ReparationCreateDto reparationDto) {

        if (!checkServiceVehicleExist(reparationDto.getVehicleId())) {
            throw new ReservationConflictException("Service Vehicle not found");
        }

        ServiceVehicle serviceVehicle = (ServiceVehicle) vehicleRepository.findById(reparationDto.getVehicleId())
                .orElseThrow(() -> new RessourceNotFoundException("Service Vehicle not found"));

        Reparation reparation = reparationMapper.toEntity(reparationDto);
        reparation.setServiceVehicle(serviceVehicle);

        if (!checkDateForCreateReparation(reparation)) {
            throw new IllegalArgumentException("A reparation already exists for this service vehicle.");
        }
        reparation = reparationRepository.save(reparation);

        return reparationMapper.toDto(reparation);
    }

    /**
     * 
     * Delete a Reparation by its ID
     * 
     * @param id the ID of the Reparation to delete
     * @return the deleted ReparationDto
     */
    public ReparationResponseDto deleteReparation(long id) {
        ReparationResponseDto reparationDto = getReparationById(id);
        // Check if the reparation exists before deleting
        if (reparationDto != null) {
            reparationRepository.deleteById(id);
        }
        return reparationDto;
    }

    /**
     * Update an existing Reparation
     * 
     * @param id            the id of the Reparation to update
     * @param reparationDto the updated ReparationDto
     * @return the updated ReparationDto
     */
    public ReparationResponseDto updateReparation(long id, ReparationCreateDto reparationDto) {
        // Check if the reparation exists before updating
        Reparation reparationExisting = reparationRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Reparation not found"));

        // check if the ServiceVehicle is present, so we can add his ID to this
        // ReparationCreateDto
        if (checkServiceVehicleExist(reparationDto.getVehicleId())) {

            Optional<Vehicle> serviceVehicleOpt = vehicleRepository.findById(reparationDto.getVehicleId());
            ServiceVehicle serviceVehicle = (ServiceVehicle) serviceVehicleOpt.get();
            reparationExisting.setServiceVehicle(serviceVehicle);
            reparationExisting.setStart(reparationDto.getStart());
            reparationExisting.setEnd(reparationDto.getEnd());
            reparationExisting.setMotive(reparationDto.getMotive());
            Reparation updated = reparationRepository.save(reparationExisting);
            return reparationMapper.toDto(updated);
        } else {
            throw new ReservationConflictException("Service Vehicle not found");
        }
    }

    // /**
    // * check if a reparation already exists during reserving a carpooling
    // * @return response
    // */
    // public boolean checkDateForReparation(CarPoolingReservations
    // carPoolingReservations) {
    //
    // List<Reparation> reparation =
    // reparationRepository.findOverlappingReparations(carPoolingReservations.getCarPooling().getVehicle().getId()
    // ,carPoolingReservations.getCarPooling().getDeparture(),carPoolingReservations.getCarPooling().getArrival());
    //
    // return reparation.isEmpty();
    // }

    /**
     * check if a reparation already exists during reserving a carpooling
     * 
     * @return response
     */
    public boolean checkDateForCreateReparation(Reparation reparation) {

        List<Reparation> reparationList = reparationRepository
                .findByServiceVehicleId(reparation.getServiceVehicle().getId());
        return reparationList.isEmpty();

    }

    /**
     * Check if a serviceVehicle exists in database
     * 
     * @return true of false
     */
    public boolean checkServiceVehicleExist(Long id) {
        return serviceVehicleRepository.findById(id).isPresent();
    }
}
