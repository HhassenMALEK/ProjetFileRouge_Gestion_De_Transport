package com.api.ouimouve.controller;

import com.api.ouimouve.dto.ReparationDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.ReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reparation")
public class ReparationController {
    @Autowired
    private ReparationService reparationService;

    @GetMapping("/list/{vehicleId}")
    public List<ReparationDto>  getAllReparations(@PathVariable long vehicleId) throws RessourceNotFoundException {
        // TODO test l'existence du véhicule
        List<ReparationDto> reparations = reparationService.getAllReparations(vehicleId);
        if (reparations.isEmpty()) {
            throw new RessourceNotFoundException("No reparations found for vehicle ID: " + vehicleId);
        }
        return reparations;
    }

    @GetMapping("/{id}")
    public ReparationDto getReparationById(@PathVariable long id) throws RessourceNotFoundException {
        ReparationDto reparation = reparationService.getReparationById(id);
        if (reparation == null) {
            throw new RessourceNotFoundException("The reparation does not exist");
        }
        return reparation;
    }

    @DeleteMapping("/{id}")
    public ReparationDto deleteReparation(@PathVariable long id) throws RessourceNotFoundException {
        ReparationDto reparation = reparationService.deleteReparation(id);
        if (reparation == null) {
            throw new RessourceNotFoundException("The reparation does not exist");
        }
        return reparation;
    }

    @PatchMapping("/{id}")
    public ReparationDto updateReparation(@PathVariable long id, @RequestBody ReparationDto reparationDto) throws RessourceNotFoundException {
        ReparationDto updatedReparation = reparationService.updateReparation(id, reparationDto);
        if (updatedReparation == null) {
            throw new RessourceNotFoundException("The reparation does not exist");
        }
        return updatedReparation;
    }

    @PostMapping()
    public ReparationDto createReparation(@RequestBody ReparationDto reparationDto) throws RessourceNotFoundException {
        // TODO test l'existence du véhicule
        return reparationService.createReparation(reparationDto);
    }
}
