package com.api.ouimouve.service;

import com.api.ouimouve.dto.ReparationDto;
import com.api.ouimouve.mapper.ReparationMapper;
import com.api.ouimouve.repository.ReparationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReparationService {
    @Autowired
    private ReparationRepository reparationRepository;
    @Autowired
    private ReparationMapper reparationMapper;

    // Add methods to handle CRUD operations for Reparation entities
    /**
     * Get all Reparations
     * @return a list of ReparationDto
     */
    public List<ReparationDto>  getAllReparations(Long vehicleId) {
        return reparationRepository.findByVehicleId(vehicleId).stream()
                .map(reparationMapper::toReparationDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a Reparation by its ID
     * @param id the ID of the Reparation
     * @return the ReparationDto if found, null otherwise
     */
    public ReparationDto getReparationById(long id) {
        return reparationRepository.findById(id)
                .map(reparationMapper::toReparationDto)
                .orElse(null);
    }

    /**
     * Create a new Reparation
     * @param reparationDto the ReparationDto to create
     * @return the created ReparationDto
     */
    public ReparationDto createReparation(ReparationDto reparationDto) {
        return reparationMapper.toReparationDto(reparationRepository.save(reparationMapper.toReparation(reparationDto)));
    }

    /**
     * Update an existing Reparation
     * @param id the id of the Reparation to update
     * @return the updated ReparationDto
     */
    public ReparationDto deleteReparation(long id) {
        ReparationDto reparationDto = getReparationById(id);
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
    public ReparationDto updateReparation(long id, ReparationDto reparationDto) {
        // Check if the reparation exists before updating
        if (getReparationById(id) != null) {
            reparationRepository.findById(id).ifPresent(reparation -> {
                // TODO Contrôller qu'il n'y a pas de réparation qui se chevauche
                reparation.setStart(reparationDto.getStart());
                reparation.setEnd(reparationDto.getEnd());
                reparation.setMotive(reparationDto.getMotive());
                reparation.setVehicleId(reparationDto.getVehicleId());
                reparationRepository.save(reparation);
            });
        }
        throw new RuntimeException("Reparation not found");
    }
}
