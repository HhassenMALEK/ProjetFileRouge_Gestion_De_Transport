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

/**
 * Service class for managing carpooling operations.
 * Provides methods to create, update, delete, and retrieve carpooling information.
 */
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
     * @param the ID of the carpooling
     * @return the CarPoolingResponseDto
     */
    public CarPoolingResponseDto getCarPoolingById(Long id) {
        if (id == null) {
            throw new RessourceNotFoundException("ID requis pour rechercher un covoiturage.");
        }

        return carPoolingRepository.findById(id)
                .map(carPoolingMapper::toResponseDto)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));
    }

    /**
     * Retrieves carpoolings by their status.
     * @param status the status of the carpooling
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> getCarpoolingByStatus(CarPoolingStatus status) {
        return carPoolingRepository.findByStatus(status).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new carpooling entry.
     * @param dto containing carpooling details.
     * @return the created carpooling entry.
     */
    public CarPoolingResponseDto createCarpooling(CarPoolingCreateDto dto) {
        validateDto(dto);
        validateDateAndTime(dto.getDeparture(), dto.getArrival());
        checkOverlaps(dto.getVehicleId(), dto.getOrganizerId(), dto.getDeparture(), dto.getArrival(), -1L);
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

    /**
     * Updates an existing carpooling entry.
     * @param id the ID of the carpooling to update
     * @param dto the updated carpooling details
     * @return the updated carpooling entry
     */
    public CarPoolingResponseDto updateCarPooling(Long id, CarPoolingCreateDto dto) {
        validateDto(dto);
        validateDateAndTime(dto.getDeparture(), dto.getArrival());

        CarPooling entity = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));

        checkOverlaps(dto.getVehicleId(), dto.getOrganizerId(), dto.getDeparture(), dto.getArrival(), id);

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
        entity.setOrganizer(userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RessourceNotFoundException("Organisateur introuvable")));

        return carPoolingMapper.toResponseDto(carPoolingRepository.save(entity));
    }

    /**
     * Deletes a carpooling entry by its ID.
     * @param id the ID of the carpooling entry
     */
    public void deleteCarpooling(Long id) {
        CarPooling carPooling = carPoolingRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Covoiturage introuvable avec l’ID : " + id));

        // Vérifie si la date de départ est passée
        if (carPooling.getDeparture() != null && !carPooling.getDeparture().after(new Date())) {
            throw new InvalidRessourceException("Impossible de supprimer un covoiturage déjà commencé ou passé.");
        }
        carPoolingRepository.deleteById(id);
    }

    /**
     * Retrieves carpoolings by their status and with departure after a given date.
     * @param status the status of the carpooling
     * @param date the date to filter from
     * @return a list of CarPoolingResponseDto
     */
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

    /**
     * Retrieves carpoolings by their status, ordered by departure time.
     * @param status the status of the carpooling
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> getCarPoolingsByStatusOrdered(CarPoolingStatus status) {
        return carPoolingRepository.findByStatusOrderByDepartureAsc(status).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves carpoolings scheduled after a given date.
     * @param date the date to filter from
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> findByDepartureAfter(Date date) {
        return carPoolingRepository.findByDepartureAfter(date).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves carpoolings by their organizer ID.
     * @param organizerId the ID of the organizer
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> findByOrganizerId(Long organizerId) {
        return carPoolingRepository.findByOrganizerId(organizerId).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves carpoolings by their vehicle ID.
     * @param vehicleId the ID of the vehicle
     * @return a list of CarPoolingResponseDto
     */
    public List<CarPoolingResponseDto> findByVehicleId(Long vehicleId) {
        return carPoolingRepository.findByVehicleId(vehicleId).stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }


    /**
     * Filters carpoolings based on optional criteria:
     * organizer ID, status, departure time range (start and end date), and vehicle ID.
     * <p>
     * If only a start date is provided, the filter will include all carpoolings
     * scheduled on that day (from 00:00:00 to 23:59:59).
     * If both start and end dates are provided, the method ensures the full day range
     * is covered for each bound.
     *
     * @param organizerId the ID of the organizer (optional)
     * @param status the status of the carpooling (optional)
     * @param startDate the start of the departure date range (optional)
     * @param endDate the end of the departure date range (optional)
     * @param vehicleId the ID of the vehicle (optional)
     * @return a list of carpoolings matching the given filters
     */
    public List<CarPoolingResponseDto> filterByStatusDateVehicle(
            Long organizerId,
            CarPoolingStatus status,
            Date startDate,
            Date endDate,
            Long vehicleId
    ) {
        if (startDate != null && endDate == null) {
            startDate = truncateToStartOfDay(startDate);
            endDate = toEndOfDay(startDate);
        } else if (startDate != null && endDate != null) {
            startDate = truncateToStartOfDay(startDate);
            endDate = toEndOfDay(endDate);
        }

        return carPoolingRepository.filterCarpoolings(organizerId, status, startDate, endDate, vehicleId)
                .stream()
                .map(carPoolingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts the given date to the start of the day (00:00:00) in the system default time zone.
     *
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
     * Converts the given date to the end of the day (23:59:59) in the system default time zone.
     *
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
     * Validates the CarPoolingCreateDto object.
     * @param dto the CarPoolingCreateDto object to validate
     */
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
        if (dto.getOrganizerId() == null) {
            throw new InvalidRessourceException("L'organisateur est obligatoire.");
        }
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
     * Validates that there are no overlapping carpoolings for the given vehicle.
     * @param vehicleId the id of  the vehicle
     * @param start the start date
     * @param end the end date
     * @param excludeId the ID to exclude from the check
     */
    private void validateNoVehicleOverlap(Long vehicleId, Date start, Date end, Long excludeId) {
        var conflicts = carPoolingRepository.findOverlappingCarPoolingByVehicleExcludingId(vehicleId, start, end, excludeId);
        if (!conflicts.isEmpty()) {
            throw new InvalidRessourceException("Le véhicule sélectionné est déjà utilisé sur un autre créneau.");
        }
    }

    /**
     * Validates that there are no overlapping carpoolings for the given organizer.
     * @param organizerId the id of the organizer
     * @param start the start date
     * @param end the end date
     * @param excludeId the ID to exclude from the check
     */
    private void validateNoOrganizerOverlap(Long organizerId, Date start, Date end, Long excludeId) {
        List<CarPooling> conflicts = carPoolingRepository.findOverlappingCarPoolingByOrganizer(organizerId, start, end, excludeId);
        if (!conflicts.isEmpty()) {
            throw new InvalidRessourceException("Vous avez déjà un covoiturage prévu sur ce créneau.");
        }
    }

    /**
     * Validates date and time departure  (doit être dans le futur et entre 00:00 et 23:59).
     */
    private void validateDateAndTime(Date departure, Date arrival) {
        if (departure.before(new Date())) {
            throw new InvalidRessourceException("La date de départ doit être ultérieure à aujourd'hui.");
        }
        LocalTime time = departure.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        if (time.isBefore(LocalTime.of(0, 0)) || time.isAfter(LocalTime.of(23, 59))) {
            throw new InvalidRessourceException("Heure invalide.");
        }
    }

    /**
     * Vérifie les chevauchements de créneaux pour un véhicule et un organisateur.
     */
    private void checkOverlaps(Long vehicleId, Long organizerId, Date start, Date end, Long excludeId) {
        var vehicleConflicts = carPoolingRepository.findOverlappingCarPoolingByVehicleExcludingId(vehicleId, start, end, excludeId);
        if (!vehicleConflicts.isEmpty()) {
            throw new InvalidRessourceException("Le véhicule est déjà réservé sur ce créneau.");
        }
        var userConflicts = carPoolingRepository.findOverlappingCarPoolingByOrganizer(organizerId, start, end, excludeId);
        if (!userConflicts.isEmpty()) {
            throw new InvalidRessourceException("L’organisateur a déjà un covoiturage prévu sur ce créneau.");
        }
    }

}
