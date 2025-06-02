package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.utils.Email;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.CarPoolingMapper;
import com.api.ouimouve.repository.*;
import com.api.ouimouve.validation.CarPoolingValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing carpooling operations.
 * Provides methods to create, update, delete, and retrieve carpooling information.
 */
@Slf4j
@Service
public class CarPoolingService {
    /**
     * Repository for carpooling-related data access.
     */
    @Autowired
    private CarPoolingRepository carPoolingRepository;
    /**
     * Mapper for converting between CarPooling entity and DTOs.
     */
    @Autowired
    private CarPoolingMapper carPoolingMapper;
    /**
     * Repository for address-related data access.
     */
    @Autowired
    private SiteRepository siteRepository;
    /**
     * Repository for user-related data access.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Repository for vehicle-related data access.
     */
    @Autowired
    private VehicleRepository vehicleRepository;
    /**
     * Email utility for sending notifications.
     */
    @Autowired
    private Email email;
    /**
     * Validator for carpooling data.
     */
    @Autowired
    private CarPoolingValidator carPoolingValidator;

    /**
     * Retrieves all carpoolings.
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> getAllCarpooling() {
        return carPoolingRepository.findAll().stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a carpooling by its ID.
     * @param id the ID of the carpooling
     * @return the CarPoolingResponseDto
     */
    public CarPoolingResponseDto getCarPoolingById(Long id) {
        if (id == null) {
            throw new RessourceNotFoundException("ID requis pour rechercher un covoiturage.");
        }

        return carPoolingRepository.findById(id)
                .map(carPoolingMapper::toResponseDto)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l'ID : " + id));
    }

    /**
     * Creates a new carpooling entry.
     * @param dto containing carpooling details.
     * @return the created carpooling entry.
     */
    public CarPoolingResponseDto createCarpooling(CarPoolingCreateDto dto) {
        carPoolingValidator.validate(dto, null);
        CarPooling carPooling = carPoolingMapper.toEntity(dto);
        populateEntityReferences(carPooling, dto);
        CarPooling saved = carPoolingRepository.save(carPooling);
        //send an email alert to the organizer
        email.sendAlert(
                saved.getOrganizer().getEmail(),
                "Covoiturage créé avec succès",
                "Votre covoiturage prévu pour le " + saved.getDeparture() + " a bien été enregistré."
        );
        return carPoolingMapper.toResponseDto(saved);
    }

    /**
     * Updates an existing carpooling entry.
     * @param id the ID of the carpooling to update
     * @param dto the updated carpooling details
     * @return the updated carpooling entry
     */
    public CarPoolingResponseDto updateCarPooling(Long id, CarPoolingCreateDto dto) {
        carPoolingValidator.validate(dto, id);
        CarPooling entity = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l'ID : " + id));
        entity.setDeparture(dto.getDeparture());
        entity.setStatus(dto.getStatus());
        entity.setDurationInMinutes(dto.getDurationInMinutes());
        entity.setDistance(dto.getDistance());
        populateEntityReferences(entity, dto);
        CarPooling updated = carPoolingRepository.save(entity);
        // Send an email alert
        email.sendAlert(
                updated.getOrganizer().getEmail(),
                "Mise à jour de votre covoiturage",
                "Votre covoiturage a été modifié. Nouvelle date de départ : " + updated.getDeparture()
        );
        return carPoolingMapper.toResponseDto(updated);
    }

    /**
     * Deletes a carpooling entry by its ID.
     * @param id the ID of the carpooling entry
     */
    public void deleteCarpooling(Long id) {
        CarPooling carPooling = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l'ID : " + id));
        // Check if the departure date has already passed
        carPoolingValidator.checkDeletable(carPooling);
        carPoolingRepository.deleteById(id);
        //send an email to the organizer
        email.sendAlert(
                carPooling.getOrganizer().getEmail(),
                "Covoiturage annulé",
                "Votre covoiturage prévu pour le " + carPooling.getDeparture() + " a été supprimé."
        );
    }

    /**
     * Converts the given date to the end of the day (23:59:59).
     * @param date the original date
     * @return a new Date object set to 23:59:59 of the same day
     */
    private Date toEndOfDay(Date date) {
        return Date.from(date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * Filters carpoolings based on optional criteria.
     * @param organizerId the ID of the organizer (optional)
     * @param status the status of the carpooling (optional)
     * @param startDate the start of the departure date range (optional)
     * @param vehicleId the ID of the vehicle (optional)
     * @return a list of carpoolings matching the given filters
     */
    public List<CarPoolingResponseDto> getCarPoolingByFilter(
            Long organizerId,
            CarPoolingStatus status,
            String startDate,
            String endDate,
            String nameDeparture,
            String nameDestination,
            Long vehicleId,
            Integer capacity
    ) {
        Date dateBegin = null;
        if (startDate != null ) {
            LocalDate localDate = LocalDate.parse(startDate);
            dateBegin = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        Date dateFinal = null;
        if (endDate != null) {
            LocalDate localDate = LocalDate.parse(endDate);
            Date dateAtStartOfDay = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateFinal = toEndOfDay(dateAtStartOfDay);
        }
        Site departureSite = null;
        if(nameDeparture != null){
            departureSite = siteRepository.findByName(nameDeparture)
                    .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable : " + nameDeparture));
        }
        Site destinationSite = null;
        if(nameDestination != null){
            destinationSite = siteRepository.findByName(nameDestination)
                    .orElseThrow(() -> new RessourceNotFoundException("Adresse de destination introuvable : " + nameDestination));
        }
        return carPoolingRepository
                .filterCarpoolings(organizerId, status, dateBegin, dateFinal, departureSite.getId(), destinationSite.getId(), vehicleId)
                .stream()
                .filter(carpooling -> capacity == null || carpooling.getNbSeatAvailable() >= capacity)
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Populates entity references from a DTO.
     * @param entity the entity to populate
     * @param dto the source DTO
     */
    private void populateEntityReferences(CarPooling entity, CarPoolingCreateDto dto) {
        entity.setDepartureSite(siteRepository.findById(dto.getDepartureSiteId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable")));

        entity.setDestinationSite(siteRepository.findById(dto.getDestinationSiteId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de destination introuvable")));

        entity.setVehicle(vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RessourceNotFoundException("Véhicule introuvable")));

        entity.setOrganizer(userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RessourceNotFoundException("Organisateur introuvable")));
    }

}
