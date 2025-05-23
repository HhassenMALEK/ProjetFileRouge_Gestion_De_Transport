package com.api.ouimouve.service;

import com.api.ouimouve.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
     @Autowired
    private VehicleRepository vehicleRepository;
     public boolean doesImmatriculationExist(String immatriculation) {
         return vehicleRepository.findByImmatriculation(immatriculation).isPresent();
     }
}
