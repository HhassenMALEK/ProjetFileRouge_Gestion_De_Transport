package com.api.ouimouve.controller;

import com.api.ouimouve.dto.ServiceVehicleCreateDto;
import com.api.ouimouve.dto.ServiceVehicleDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.ServiceVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-vehicle")
public class ServiceVehicleController {
    @Autowired
    private ServiceVehicleService serviceVehicleService;

    @GetMapping("/{id}")
    public ServiceVehicleDto getServiceVehicle(@PathVariable Long id) throws RessourceNotFoundException {
        return serviceVehicleService.getServiceVehicleById(id);
    }


    @PostMapping
    public ServiceVehicleDto createServiceVehicle(@RequestBody ServiceVehicleCreateDto serviceVehicleDto) throws InvalidRessourceException {
        return serviceVehicleService.createServiceVehicle(serviceVehicleDto);
    }

    @PutMapping("/{id}")
    public ServiceVehicleDto updateServiceVehicle(@PathVariable Long id, @RequestBody ServiceVehicleCreateDto serviceVehicleDto) throws RessourceNotFoundException {
        return serviceVehicleService.updateServiceVehicle(id, serviceVehicleDto);
    }

    @DeleteMapping("/{id}")
    public ServiceVehicleDto deleteServiceVehicle(@PathVariable Long id) throws RessourceNotFoundException {
        return serviceVehicleService.deleteServiceVehicle(id);
    }
}
