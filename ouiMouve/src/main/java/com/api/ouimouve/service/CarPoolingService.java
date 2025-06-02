package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.dto.SiteResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.utils.Email;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.CarPoolingMapper;
import com.api.ouimouve.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
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

    @Autowired
    private Email email;

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
        validateCarPooling(dto, null);
        CarPooling carPooling = carPoolingMapper.toEntity(dto);
        populateEntityReferences(carPooling, dto);
        // Sauvegarde
        CarPooling saved = carPoolingRepository.save(carPooling);
        // Envoi de l'email à l'organisateur
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
        validateCarPooling(dto, id);

        CarPooling entity = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l'ID : " + id));

        // Mise à jour des propriétés de base
        entity.setDeparture(dto.getDeparture());
        entity.setStatus(dto.getStatus());
        entity.setDurationInMinutes(dto.getDurationInMinutes());
        entity.setDistance(dto.getDistance());

        // Mise à jour des références
        populateEntityReferences(entity, dto);
// Sauvegarde de l'entité mise à jour
        CarPooling updated = carPoolingRepository.save(entity);

        // Envoi de l'email avec l'entité mise à jour
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
        // Vérifie si la date de départ est passée
        if (carPooling.getDeparture() != null && !carPooling.getDeparture().after(new Date())) {
            throw new InvalidRessourceException("Impossible de supprimer un covoiturage déjà commencé ou passé.");
        }
        carPoolingRepository.deleteById(id);
        // Envoi d'une alerte par email
        email.sendAlert(
                carPooling.getOrganizer().getEmail(),
                "Covoiturage annulé",
                "Votre covoiturage prévu pour le " + carPooling.getDeparture() + " a été supprimé."
        );
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



        return carPoolingRepository.filterCarpoolings(organizerId, status, dateBegin, dateFinal, departureSite.getId(), destinationSite.getId(), vehicleId)
                .stream()
                .filter(carpooling -> capacity == null || carpooling.getNbSeatAvailable() >= capacity)
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Validates all aspects of a carpooling entry.
     * @param dto the carpooling data to validate
     * @param excludeId ID to exclude from overlap checks (for updates)
     */
    private void validateCarPooling(CarPoolingCreateDto dto, Long excludeId) {
        validateRequiredFields(dto);
        validateDateLogic(dto);
        validateDateAndTime(dto.getDeparture());
        checkOverlaps(dto.getVehicleId(), dto.getOrganizerId(), dto.getDeparture(), dto.getArrival(), excludeId);
    }

    /**
     * Validate required fields in the DTO.
     * @param dto the DTO to validate
     */
    private void validateRequiredFields(CarPoolingCreateDto dto) {
        if (dto.getDepartureSiteId() == null) {
            throw new InvalidRessourceException("L'adresse de départ est obligatoire.");
        }
        if (dto.getDestinationSiteId() == null) {
            throw new InvalidRessourceException("L'adresse de destination est obligatoire.");
        }
        if (dto.getVehicleId() == null) {
            throw new InvalidRessourceException("Le véhicule est obligatoire.");
        }
        if (dto.getOrganizerId() == null) {
            throw new InvalidRessourceException("L'organisateur est obligatoire.");
        }
        if (dto.getDepartureSiteId().equals(dto.getDestinationSiteId())) {
            throw new InvalidRessourceException("L'adresse de départ et d'arrivée ne peuvent pas être identiques.");
        }
    }

    /**
     * Validate date logic (arrival after departure, duration matches, etc.)
     * @param dto the DTO to validate
     */
    private void validateDateLogic(CarPoolingCreateDto dto) {
        if (dto.getArrival().before(dto.getDeparture())) {
            throw new InvalidRessourceException("La date d'arrivée doit être postérieure à la date de départ.");
        }
        if (dto.getDeparture().equals(dto.getArrival())) {
            throw new InvalidRessourceException("La date de départ et d'arrivée ne peuvent pas être identiques.");
        }
        if (dto.getDurationInMinutes() != null) {
            long expected = (dto.getArrival().getTime() - dto.getDeparture().getTime()) / 60000;
            if (expected != dto.getDurationInMinutes()) {
                throw new InvalidRessourceException("La durée ne correspond pas aux horaires définis.");
            }
        }
    }

    /**
     * Validates date and time (must be in the future and between 00:00 and 23:59).
     * @param departure the departure date to validate
     */
    private void validateDateAndTime(Date departure) {
        if (departure.before(new Date())) {
            throw new InvalidRessourceException("La date de départ doit être ultérieure à aujourd'hui.");
        }
        LocalTime time = departure.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        if (time.isBefore(LocalTime.of(0, 0)) || time.isAfter(LocalTime.of(23, 59))) {
            throw new InvalidRessourceException("Heure invalide.");
        }
    }

    /**
     * Check for overlapping schedules for both vehicle and organizer.
     * @param vehicleId the vehicle ID to check
     * @param organizerId the organizer ID to check
     * @param start the start date of the carpooling
     * @param end the end date of the carpooling
     * @param excludeId the ID to exclude from the check (for updates)
     */
    private void checkOverlaps(Long vehicleId, Long organizerId, Date start, Date end, Long excludeId) {
        // Vérification des conflits de véhicule
        List<CarPooling> vehicleConflicts = carPoolingRepository.findOverlappingCarPoolingByVehicleExcludingId(
                vehicleId, start, end, excludeId);
        if (!vehicleConflicts.isEmpty()) {
            throw new InvalidRessourceException("Le véhicule est déjà réservé sur ce créneau.");
        }

        // Vérification des conflits d'organisateur
        List<CarPooling> userConflicts = carPoolingRepository.findOverlappingCarPoolingByOrganizer(
                organizerId, start, end, excludeId);
        if (!userConflicts.isEmpty()) {
            throw new InvalidRessourceException("L'organisateur a déjà un covoiturage prévu sur ce créneau.");
        }
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

    /**
     * Converts the given date to the start of the day (00:00:00).
     * @param date the original date
     * @return a new Date object set to 00:00:00 of the same day
     */
    private Date truncateToStartOfDay(Date date) {
        return Date.from(date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
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
}
