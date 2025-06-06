package com.api.ouimouve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ouimouve.dto.ReparationCreateDto;
import com.api.ouimouve.dto.ReparationResponseDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.ReparationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reparation")
public class ReparationController {
    @Autowired
    private ReparationService reparationService;

    @GetMapping("/list/{vehicleId}")
    public List<ReparationResponseDto> getAllReparations(@PathVariable long vehicleId)
            throws RessourceNotFoundException {

        List<ReparationResponseDto> reparations = reparationService.getAllReparations(vehicleId);
        if (!reparations.isEmpty()) {
            return reparations;
        } else {
            throw new RessourceNotFoundException("No reparations found for vehicle ID: " + vehicleId);
        }

    }

    @GetMapping("/{id}")
    public ReparationResponseDto getReparationById(@PathVariable long id) throws RessourceNotFoundException {
        ReparationResponseDto reparation = reparationService.getReparationById(id);
        if (reparation == null) {
            throw new RessourceNotFoundException("The reparation does not exist");
        }
        return reparation;
    }

    @DeleteMapping("/{id}")
    public ReparationResponseDto deleteReparation(@PathVariable long id) throws RessourceNotFoundException {
        ReparationResponseDto reparation = reparationService.deleteReparation(id);
        if (reparation == null) {
            throw new RessourceNotFoundException("The reparation does not exist");
        }
        return reparation;
    }

    @PatchMapping("/{id}")
    public ReparationResponseDto updateReparation(@PathVariable long id, @RequestBody ReparationCreateDto reparationDto)
            throws RessourceNotFoundException {
        ReparationResponseDto updatedReparation = reparationService.updateReparation(id, reparationDto);
        if (updatedReparation == null) {
            throw new RessourceNotFoundException("The reparation does not exist");
        }
        return updatedReparation;
    }

    @PostMapping()
    public ReparationResponseDto createReparation(@RequestBody @Valid ReparationCreateDto reparationDto)
            throws RessourceNotFoundException {
        // TODO test l'existence du véhicule
        return reparationService.createReparation(reparationDto);
    }
}
