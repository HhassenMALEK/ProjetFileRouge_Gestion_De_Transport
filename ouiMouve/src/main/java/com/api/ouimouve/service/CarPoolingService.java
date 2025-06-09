package com.api.ouimouve.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.CarPoolingMapper;
import com.api.ouimouve.repository.CarPoolingRepository;
import com.api.ouimouve.repository.SiteRepository;
import com.api.ouimouve.utils.DateUtils;
import com.api.ouimouve.utils.Email;
import com.api.ouimouve.validation.CarPoolingValidator;
import com.api.ouimouve.exception.InvalidRessourceException;


import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing carpooling operations.
 * Provides methods to create, update, delete, and retrieve carpooling
 * information.
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
     * 
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> getAllCarpooling() {
        return carPoolingRepository.findAll().stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a carpooling by its ID.
     * 
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
     * 
     * @param dto containing carpooling details.
     * @return the created carpooling entry.
     */
    public CarPoolingResponseDto createCarpooling(CarPoolingCreateDto dto) {
        // Étape 1: Validation du DTO (cela inclut le calcul de l'arrivée si nécessaire)
        carPoolingValidator.validate(dto, null);

        // 2. Calcul de la date d'arrivée après validation
        Date arrival = carPoolingValidator.calculateArrival(dto.getDeparture(), dto.getDurationInMinutes());

        // 3. Vérification des chevauchements pour le véhicule et l'organisateur
        // Passer la date d'arrivée calculée dans la requête
        List<CarPooling> overlappingCarpoolings = carPoolingRepository.findOverlappingCarPoolingByVehicleExcludingId(
                dto.getVehicleId(), dto.getDeparture(), arrival, null);

        // Si des chevauchements existent, il faut gérer l'erreur
        if (!overlappingCarpoolings.isEmpty()) {
            throw new InvalidRessourceException("Le véhicule est déjà réservé sur ce créneau.");
        }

        // Vérification des chevauchements pour l'organisateur
        overlappingCarpoolings = carPoolingRepository.findOverlappingCarPoolingByOrganizer(
                dto.getOrganizerId(), dto.getDeparture(), arrival, null);

        if (!overlappingCarpoolings.isEmpty()) {
            throw new InvalidRessourceException("L'organisateur a déjà un covoiturage prévu sur ce créneau.");
        }

        // 4. Transformation du DTO en entité
        CarPooling carPooling = carPoolingMapper.toEntity(dto);

        // 5. Vérification des entités associées (adresse, véhicule, organisateur)
        carPoolingValidator.checkInput(carPooling, dto);

        // 6. Enregistrement de l'entité dans la base de données
        CarPooling saved = carPoolingRepository.save(carPooling);

        // 7. Envoi de l'email de confirmation à l'organisateur
        email.sendAlert(
                saved.getOrganizer().getEmail(),
                "Covoiturage créé avec succès",
                "Votre covoiturage prévu pour le " + saved.getDeparture() + " a bien été enregistré.");

        // 8. Retour de l'entité convertie en DTO pour la réponse
        return carPoolingMapper.toResponseDto(saved);
    }


    /**
     * Updates an existing carpooling entry.
     * 
     * @param id  the ID of the carpooling to update
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
        carPoolingValidator.checkInput(entity, dto);
        CarPooling updated = carPoolingRepository.save(entity);
        // Send an email alert
        email.sendAlert(
                updated.getOrganizer().getEmail(),
                "Mise à jour de votre covoiturage",
                "Votre covoiturage a été modifié. Nouvelle date de départ : " + updated.getDeparture());
        return carPoolingMapper.toResponseDto(updated);
    }

    /**
     * Deletes a carpooling entry by its ID.
     * 
     * @param id the ID of the carpooling entry
     */
    public void deleteCarpooling(Long id) {
        CarPooling carPooling = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l'ID : " + id));
        // Check if the departure date has already passed
        carPoolingValidator.checkDeletable(carPooling);
        carPoolingRepository.deleteById(id);
        // send an email to the organizer
        email.sendAlert(
                carPooling.getOrganizer().getEmail(),
                "Covoiturage annulé",
                "Votre covoiturage prévu pour le " + carPooling.getDeparture() + " a été supprimé.");
    }

    /**
     * Filters carpoolings based on optional criteria.
     * 
     * @param organizerId the ID of the organizer (optional)
     * @param status      the status of the carpooling (optional)
     * @param startDate   the start of the departure date range (optional)
     * @param vehicleId   the ID of the vehicle (optional)
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
            Integer capacity) {
        Date dateBegin = null;
        if (startDate != null) {
            LocalDate localDate = LocalDate.parse(startDate);
            dateBegin = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        Date dateFinal = null;
        if (endDate != null) {
            LocalDate localDate = LocalDate.parse(endDate);
            Date dateAtStartOfDay = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateFinal = DateUtils.toEndOfDay(dateAtStartOfDay);
        }

        Long departureSiteId = null;
        if (nameDeparture != null) {
            Site departureSite = siteRepository.findByName(nameDeparture)
                    .orElseThrow(
                            () -> new RessourceNotFoundException("Adresse de départ introuvable : " + nameDeparture));
            departureSiteId = departureSite.getId();
        }

        Long destinationSiteId = null;
        if (nameDestination != null) {
            Site destinationSite = siteRepository.findByName(nameDestination)
                    .orElseThrow(() -> new RessourceNotFoundException(
                            "Adresse de destination introuvable : " + nameDestination));
            destinationSiteId = destinationSite.getId();
        }
        return carPoolingRepository
                .filterCarpoolings(organizerId, status, dateBegin, dateFinal, departureSiteId, destinationSiteId,
                        vehicleId)
                .stream()
                .filter(carpooling -> capacity == null || carpooling.getNbSeatAvailable() >= capacity)
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}