package com.api.ouimouve.controller;

import com.api.ouimouve.dto.PersonalVehicleCreateDto;
import com.api.ouimouve.dto.PersonalVehicleDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.PersonalVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal-vehicle")
public class PersonalVehicleController {
    @Autowired
    private PersonalVehicleService personalVehicleService;

    @GetMapping("/{id}")
    public PersonalVehicleDto getPersonalVehicle(@PathVariable Long id) throws RessourceNotFoundException {
        return personalVehicleService.getPersonalVehicleById(id);
    }

    @GetMapping("/list")
    public List<PersonalVehicleDto> getPersonalVehiclesByUserId() throws RessourceNotFoundException {
        return personalVehicleService.getPersonalVehiclesByUserId();
    }

    @PostMapping
    public PersonalVehicleDto createPersonalVehicle(@RequestBody PersonalVehicleCreateDto personalVehicleDto) throws InvalidRessourceException {
        return personalVehicleService.createPersonalVehicle(personalVehicleDto);
    }

    @PutMapping("/{id}")
    public PersonalVehicleDto updatePersonalVehicle(@PathVariable Long id, @RequestBody PersonalVehicleCreateDto personalVehicleDto) throws RessourceNotFoundException {
        return personalVehicleService.updatePersonalVehicle(id, personalVehicleDto);
    }

    @DeleteMapping("/{id}")
    public PersonalVehicleDto deletePersonalVehicle(@PathVariable Long id) throws RessourceNotFoundException {
        return personalVehicleService.deletePersonalVehicle(id);
    }
}
