package com.api.ouimouve.validation;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.repository.CarPoolingRepository;
import com.api.ouimouve.repository.SiteRepository;
import com.api.ouimouve.repository.UserRepository;
import com.api.ouimouve.repository.VehicleRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class CarPoolingValidator {
    private final CarPoolingRepository carPoolingRepository;
    private final SiteRepository siteRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;


    public CarPoolingValidator(
            CarPoolingRepository carPoolingRepository,
            SiteRepository siteRepository,
            VehicleRepository vehicleRepository,
            UserRepository userRepository){
        this.carPoolingRepository = carPoolingRepository;
        this.siteRepository = siteRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;

        }

    /**
     * Validates all aspects of a carpooling entry.
     * @param dto the carpooling data to validate
     * @param excludeId ID to exclude from overlap checks (for updates)
     */
    public void validate(CarPoolingCreateDto dto, Long excludeId) {
        validateRequiredFields(dto);
        validateDateLogic(dto);
        validateDateAndTime(dto.getDeparture());
        checkOverlaps(dto.getVehicleId(), dto.getOrganizerId(), dto.getDeparture(), dto.getArrival(), excludeId);
    }
    /**
     * Populates entity references from a DTO.
     * @param entity the entity to populate
     * @param dto the source DTO
     */
    public void checkInput(CarPooling entity, CarPoolingCreateDto dto) {
        entity.setDepartureSite(siteRepository.findById(dto.getDepartureSiteId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de départ introuvable")));

        entity.setDestinationSite(siteRepository.findById(dto.getDestinationSiteId())
                .orElseThrow(() -> new RessourceNotFoundException("Adresse de destination introuvable")));

        entity.setVehicle(vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RessourceNotFoundException("Véhicule introuvable")));

        entity.setOrganizer(userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RessourceNotFoundException("Organisateur introuvable")));
    }

    public void checkDeletable(CarPooling carPooling) {
        if (carPooling.getDeparture() != null && !carPooling.getDeparture().after(new Date())) {
            throw new InvalidRessourceException("Impossible de supprimer un covoiturage déjà commencé ou passé.");
        }
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
            throw new InvalidRessourceException("La date d'arrivée doit être inférieur à la date de départ.");
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
        // Vehicle conflict check
        List<CarPooling> vehicleConflicts = carPoolingRepository.findOverlappingCarPoolingByVehicleExcludingId(
                vehicleId, start, end, excludeId);
        if (!vehicleConflicts.isEmpty()) {
            throw new InvalidRessourceException("Le véhicule est déjà réservé sur ce créneau.");
        }
        // Organizer conflict check
        List<CarPooling> userConflicts = carPoolingRepository.findOverlappingCarPoolingByOrganizer(
                organizerId, start, end, excludeId);
        if (!userConflicts.isEmpty()) {
            throw new InvalidRessourceException("L'organisateur a déjà un covoiturage prévu sur ce créneau.");
        }
    }
}
