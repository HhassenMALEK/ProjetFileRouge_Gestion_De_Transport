package com.api.ouimouve.service;

import com.api.ouimouve.bo.Reparation;
import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.bo.Vehicle;
import com.api.ouimouve.dto.ReparationCreateDto;
import com.api.ouimouve.dto.ReparationResponseDto;
import com.api.ouimouve.dto.VehicleReservationDto;
import com.api.ouimouve.enumeration.VehicleStatus;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.ReparationMapper;
import com.api.ouimouve.repository.ReparationRepository;
import com.api.ouimouve.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReparationService {
    @Autowired
    private ReparationRepository reparationRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ReparationMapper reparationMapper;
    @Autowired
    private ReservationService reservationService;



    // Add methods to handle CRUD operations for Reparation entities
    /**
     * Get all Reparations
     * @return a list of ReparationDto
     */
    public List<ReparationResponseDto>  getAllReparations(Long vehicleId) {
        return reparationRepository.findByServiceVehicleId(vehicleId).stream()
                .map(reparationMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a Reparation by its ID
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
     * @param reparationDto the ReparationDto to create
     * @return the created ReparationDto
     */
    public ReparationResponseDto createReparation(ReparationCreateDto reparationDto) {

        //mapping DTO without the vehicle
        Reparation reparation = reparationMapper.toEntity(reparationDto);

//        if(checkDateForReparation(reparation)) {

            //check if the ServiceVehicle is present, so we can add his ID to this ReparationCreateDto
            Optional<Vehicle> serviceVehicleOpt = vehicleRepository.findById(reparationDto.getVehicleId());

            if (serviceVehicleOpt.isPresent()) {
                ServiceVehicle serviceVehicle = (ServiceVehicle) serviceVehicleOpt.get();
                reparation.setServiceVehicle(serviceVehicle);
                reparation = reparationRepository.save(reparation);
                return reparationMapper.toDto(reparation);
            } else {
                throw new RessourceNotFoundException("Service Vehicle not found");
            }
//        } else{
//            String body = "your reservation is cancelled, cause reparation of this vehicle"+ reparation.getServiceVehicle().getImmatriculation();
//
//            Mails.sendMAilCarpoolingIsCanceledForReparation()
//        }
    }

    /**

     * Delete a Reparation by its ID
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
     * @param id the id of the Reparation to update
     * @param reparationDto the updated ReparationDto
     * @return the updated ReparationDto
     */
    public ReparationResponseDto updateReparation(long id, ReparationCreateDto reparationDto) {
        // Check if the reparation exists before updating
        Reparation reparationExisting = reparationRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Reparation not found"));

        //check if the ServiceVehicle is present, so we can add his ID to this ReparationCreateDto
        Optional<Vehicle> serviceVehicleOpt = vehicleRepository.findById(reparationDto.getVehicleId());

        if (serviceVehicleOpt.isPresent()) {
            reparationExisting.setServiceVehicle((ServiceVehicle) serviceVehicleOpt.get());
            reparationExisting.setStart(reparationDto.getStart());
            reparationExisting.setEnd(reparationDto.getEnd());
            reparationExisting.setMotive(reparationDto.getMotive());

            //Saving changes elements
            Reparation updated = reparationRepository.save(reparationExisting);
            return reparationMapper.toDto(updated);

        }else{
            throw new RessourceNotFoundException("Service Vehicle not found");
        }
    }

    public boolean checkDateForReparation(Reparation reparation) {

        boolean response =true;

        List<VehicleReservationDto> vehicleReservation = reservationService.getAllReservationsByVehicle(reparation.getServiceVehicle().getId());
        for (VehicleReservationDto vehicleReservationDto : vehicleReservation){
            if( vehicleReservationDto.getStart() == reparation.getStart() &&
                    vehicleReservationDto.getEnd() == reparation.getEnd()){
                response= false;
                vehicleReservationDto.setStatus(VehicleStatus.DISABLED);
                break;
            }
        }
        return response;
    }
}
