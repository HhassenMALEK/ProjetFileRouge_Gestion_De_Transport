package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.CarPoolingMapper;
import com.api.ouimouve.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private VehicleRepository vehicleRepository;


  //findAll
    public List<CarPoolingResponseDto> getAllCarpooling() {
        return carPoolingRepository.findAll().stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    //findById
    public CarPoolingResponseDto getCarPoolingById(Long id) {
        if (id == null) {
            throw new RessourceNotFoundException("ID requis pour rechercher un covoiturage.");
        }

        return carPoolingRepository.findById(id)
                .map(carPoolingMapper::toResponseDto)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));
    }

    //findByStatus
    public List<CarPoolingResponseDto> getCarpoolingByStatus(CarPoolingStatus status) {
        return carPoolingRepository.findByStatus(status).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Creates a new carpooling. */
    public CarPoolingResponseDto createCarpooling(CarPoolingCreateDto dto) {
        validateDto(dto);
        // Vérification date et heure
        if (dto.getDeparture().before(new Date())) {
            throw new InvalidRessourceException("La date de départ doit être ultérieure à aujourd'hui.");
        }
        // Vérification heure (si champ séparé, à adapter)
        if (dto.getDeparture() != null) {
            LocalTime time = dto.getDeparture().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
            if (time.isBefore(LocalTime.of(0, 0)) || time.isAfter(LocalTime.of(23, 59))) {
                throw new InvalidRessourceException("Heure invalide.");
            }
        }
        //Vérification de conflit utilisateur et vehicule
        validateNoVehicleOverlap(dto.getVehicleId(), dto.getDeparture(), dto.getArrival(), -1L);
        validateNoOrganizerOverlap(dto.getOrganizerId(), dto.getDeparture(), dto.getArrival(), -1L);
        CarPooling carPooling = new CarPooling();
        carPooling.setDeparture(dto.getDeparture());
        carPooling.setArrival(dto.getArrival());
        carPooling.setStatus(dto.getStatus());
        carPooling.setDistance(dto.getDistance());
        carPooling.setDurationInMinutes(dto.getDurationInMinutes());
       // CarPooling carPooling = carPoolingMapper.toEntity(dto);
        carPooling.setDepartureAdress(adressRepository.findById(dto.getDepartureAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable")));
        carPooling.setDestinationAdress(adressRepository.findById(dto.getDestinationAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de destination introuvable")));
        carPooling.setVehicle(vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RessourceNotFoundException("Véhicule introuvable")));
        //utlisateur qui fait la requette a trouver
        carPooling.setOrganizer(userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RessourceNotFoundException("Organisateur introuvable")));
        return carPoolingMapper.toResponseDto(carPoolingRepository.save(carPooling));
    }

   /** update Carpooling.  */
    public CarPoolingResponseDto updateCarPooling(Long id, CarPoolingCreateDto dto) {
        validateDto(dto);

        CarPooling entity = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));

        // Vérification date future
        if (dto.getDeparture().before(new Date())) {
            throw new InvalidRessourceException("La date de départ doit être ultérieure à aujourd'hui.");
        }
        // Vérification heure
        if (dto.getDeparture() != null) {
            LocalTime time = dto.getDeparture().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            if (time.isBefore(LocalTime.of(0, 0)) || time.isAfter(LocalTime.of(23, 59))) {
                throw new InvalidRessourceException("Heure invalide.");
            }
        }

        // Vérifier chevauchements véhicule et organisateur
        validateNoVehicleOverlap(dto.getVehicleId(), dto.getDeparture(), dto.getArrival(), id);
        validateNoOrganizerOverlap(dto.getOrganizerId(), dto.getDeparture(), dto.getArrival(), id); // il faut surcharger cette méthode

        // Mise à jour des champs
        entity.setDeparture(dto.getDeparture());
        entity.setArrival(dto.getArrival());
        entity.setStatus(dto.getStatus());
        entity.setDurationInMinutes(dto.getDurationInMinutes());
        entity.setDistance(dto.getDistance());

        entity.setDepartureAdress(adressRepository.findById(dto.getDepartureAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable")));

        entity.setDestinationAdress(adressRepository.findById(dto.getDestinationAddressId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de destination introuvable")));

        entity.setVehicle(vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RessourceNotFoundException("Véhicule introuvable")));

        // (Optionnel) Réassigner l’organisateur s’il peut être modifié :
        entity.setOrganizer(userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RessourceNotFoundException("Organisateur introuvable")));
        return carPoolingMapper.toResponseDto(carPoolingRepository.save(entity));
    }

    /** delete Carpooling.  */
    public void deleteCarpooling(Long id) {
        CarPooling carPooling = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));

        // Vérifie si la date de départ est passée
        if (carPooling.getDeparture() != null && !carPooling.getDeparture().after(new Date())) {
            throw new InvalidRessourceException("Impossible de supprimer un covoiturage déjà commencé ou passé.");
        }
        carPoolingRepository.deleteById(id);
    }


    //findByStatusAndDepartureAfter
    public List<CarPoolingResponseDto> getCarPoolingsByStatusAndDate(CarPoolingStatus status, Date date) {
        return carPoolingRepository.findByStatusAndDepartureAfter(status, date).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all carpoolings with departure after the given date.
     *
     * @param date the departure date filter
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> getCarPoolingsAfterDate(Date date) {
        return carPoolingRepository.findByDepartureAfter(date).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }


    public List<CarPoolingResponseDto> getCarPoolingsByStatusOrdered(CarPoolingStatus status) {
        return carPoolingRepository.findByStatusOrderByDepartureAsc(status).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /** find after departure */
    public List<CarPoolingResponseDto> findByDepartureAfter(Date date) {
        return carPoolingRepository.findByDepartureAfter(date).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /** find by Organizer */
    public List<CarPoolingResponseDto> findByOrganizerId(Long organizerId) {
        return carPoolingRepository.findByOrganizerId(organizerId).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }
 /** find by vehicle */
    public List<CarPoolingResponseDto> findByVehicleId(Long vehicleId) {
        return carPoolingRepository.findByVehicleId(vehicleId).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<CarPoolingResponseDto> filterByStatusDateVehicle(
            Long organizerId,
            CarPoolingStatus status,
            Date departure,
            Long vehicleId
    ) {
        return carPoolingRepository.filterCarpoolings(organizerId, status, departure, vehicleId)
                .stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }


    private void validateDto(CarPoolingCreateDto dto) {
        if (dto.getDepartureAddressId() == null) {
            throw new InvalidRessourceException("L'adresse de départ est obligatoire.");
        }
        if (dto.getDestinationAddressId() == null) {
            throw new InvalidRessourceException("L'adresse de destination est obligatoire.");
        }
        if (dto.getVehicleId() == null) {
            throw new InvalidRessourceException("Le véhicule est obligatoire.");
        }
      /*  if (dto.getOrganizerId() == null) {
            throw new InvalidRessourceException("L'organisateur est obligatoire.");
        }*/

    }

    private void validateNoVehicleOverlap(Long vehicleId, Date start, Date end, Long excludeId) {
        var conflicts = carPoolingRepository.findOverlappingCarPoolingByVehicleExcludingId(vehicleId, start, end, excludeId);
        if (!conflicts.isEmpty()) {
            throw new InvalidRessourceException("Le véhicule sélectionné est déjà utilisé sur un autre créneau.");
        }
    }

    private void validateNoOrganizerOverlap(Long organizerId, Date start, Date end, Long excludeId) {
        List<CarPooling> conflicts = carPoolingRepository.findOverlappingCarPoolingByOrganizer(organizerId, start, end, excludeId);
        if (!conflicts.isEmpty()) {
            throw new InvalidRessourceException("Vous avez déjà un covoiturage prévu sur ce créneau.");
        }
    }






}
