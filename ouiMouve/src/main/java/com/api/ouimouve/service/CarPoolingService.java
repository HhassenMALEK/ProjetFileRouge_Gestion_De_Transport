package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.dto.CarPoolingDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.CarPoolingMapper;
import com.api.ouimouve.repository.AdressRepository;
import com.api.ouimouve.repository.CarPoolingRepository;
import com.api.ouimouve.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class CarPoolingService {
    @Autowired
    private CarPoolingRepository carPoolingRepository;
    @Autowired
    CarPoolingMapper carPoolingMapper;
    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


  //findAll
    public List<CarPoolingDto> getAllCarpooling() {
        return carPoolingRepository.findAll().stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }
    //findById
    public CarPoolingDto getCarPoolingById(Long id) {
        if (id == null) {
            throw new RessourceNotFoundException("ID requis pour rechercher un covoiturage.");
        }

        return carPoolingRepository.findById(id)
                .map(carPoolingMapper::toCarPoolingDto)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));
    }

    //findByStatus
    public List<CarPoolingDto> getCarpoolingByStatus(CarPoolingStatus status) {
        return carPoolingRepository.findByStatus(status).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }


    //Create CarPooling
    public CarPoolingDto createCarpooling(CarPoolingDto dto) {
        validateDto(dto);

        CarPooling carPooling = carPoolingMapper.toEntity(dto);

        carPooling.setDepartureAdress(adressRepository.findById(dto.getDepartureAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable")));

        carPooling.setDestinationAdress(adressRepository.findById(dto.getDestinationAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de destination introuvable")));

        carPooling.setOrganizer(userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RessourceNotFoundException("Organisateur introuvable")));

        return carPoolingMapper.toCarPoolingDto(carPoolingRepository.save(carPooling));
    }

    /**
     * Met à jour un covoiturage existant.
     */
    public CarPoolingDto updateCarPooling(Long id, CarPoolingDto dto) {
        if (id == null || dto == null) {
            throw new InvalidRessourceException("Les données de mise à jour sont incomplètes.");
        }

        CarPooling entity = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));

        entity.setDeparture(dto.getDeparture());
        entity.setArrival(dto.getArrival());
        entity.setStatus(dto.getStatus());
        entity.setDurationInMinutes(dto.getDurationInMinutes());
        entity.setDistance(dto.getDistance());

        entity.setDepartureAdress(adressRepository.findById(dto.getDepartureAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable")));

        entity.setDestinationAdress(adressRepository.findById(dto.getDestinationAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de destination introuvable")));

        entity.setOrganizer(userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RessourceNotFoundException("Organisateur introuvable")));

        return carPoolingMapper.toCarPoolingDto(carPoolingRepository.save(entity));
    }

    //delelte ==> revoir d'ajouter si existe et verifier les condition
    public void deleteCarpooling(Long id) {
        if (!carPoolingRepository.existsById(id)) {
            throw new RessourceNotFoundException("Impossible de supprimer : covoiturage introuvable avec l’ID : " + id);
        }
        carPoolingRepository.deleteById(id);
    }

    //deleteIfInProgress ==> à voir
    public void deleteIfInProgress(Long id) {
        carPoolingRepository.deleteByIdAndStatus(id, CarPoolingStatus.IN_PROGRESS);
    }

    //filterByStatusDateVehicle
    public List<CarPooling> filterByStatusDateVehicle(Long userId, CarPoolingStatus status, Date departure, Long vehicleId) {
        return carPoolingRepository.findByOrganizerIdAndStatusAndDepartureAndVehicleId(userId, status, departure, vehicleId);
    }

    //findByStatusAndDepartureAfter
    public List<CarPoolingDto> getCarPoolingsByStatusAndDate(CarPoolingStatus status, Date date) {
        return carPoolingRepository.findByStatusAndDepartureAfter(status, date).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }


public List<CarPooling> findOverlappingForOrganizer(Long userId, Date from, Date to) {
    return carPoolingRepository.findByOrganizerIdAndDepartureBetween(userId, from, to);
}
    /**
     * Retrieves all carpoolings with departure after the given date.
     *
     * @param date the departure date filter
     * @return a list of CarPoolingDto
     */
    public List<CarPoolingDto> getCarPoolingsAfterDate(Date date) {
        return carPoolingRepository.findByDepartureAfter(date).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }

    public List<CarPooling> findOverlappingForVehicle(Long vehicleId, Date from, Date to) {
        return carPoolingRepository.findByVehicleIdAndDepartureBetween(vehicleId, from, to);
    }

    public List<CarPoolingDto> getCarPoolingsByStatusOrdered(CarPoolingStatus status) {
        return carPoolingRepository.findByStatusOrderByDepartureAsc(status).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }

    //findByDepartureAfter
    public List<CarPoolingDto> findByDepartureAfter(Date date) {
        return carPoolingRepository.findByDepartureAfter(date).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }
    public Optional<CarPooling> findByIdAndOrganizer(Long id, Long organizerId) {
        return carPoolingRepository.findByIdAndOrganizerId(id, organizerId);
    }

    public List<CarPooling> findByVehicleId(Long vehicleId) {
        return carPoolingRepository.findByVehicleId(vehicleId);
    }


    private void validateDto(CarPoolingDto dto) {
        if (dto.getDepartureAddressId() == null) {
            throw new InvalidRessourceException("L'adresse de départ est obligatoire.");
        }
        if (dto.getDestinationAddressId() == null) {
            throw new InvalidRessourceException("L'adresse de destination est obligatoire.");
        }
        if (dto.getVehicleId() == null) {
            throw new InvalidRessourceException("Le véhicule est obligatoire.");
        }
        if (dto.getOrganizerId() == null) {
            throw new InvalidRessourceException("L'organisateur est obligatoire.");
        }

    }


}
