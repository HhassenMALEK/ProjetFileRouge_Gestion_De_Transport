package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPoolingReservations;
import com.api.ouimouve.dto.CarPoolingReservationsCreateDTO;
import com.api.ouimouve.dto.CarPoolingReservationsResponseDTO;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.mapper.CarPoolingReservationsMapper;
import com.api.ouimouve.repository.CarPoolingReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarPoolingReservationsService {
    @Autowired
    private CarPoolingReservationsRepository carPoolingReservationsRepository;
    @Autowired
    private CarPoolingReservationsMapper carPoolingReservationsMapper;
    @Autowired
    private CarPoolingService carPoolingService;
//    /**
//     * Legacy method to get all CarPoolingReservations, not used in the current implementation
//     * Get all CarPoolingReservations
//     * @return a list of CarPoolingReservationsResponseDTO
//     */
//    public List<CarPoolingReservationsResponseDTO> getAllReservations() {
//        return carPoolingReservationsRepository.findAll()
//                .stream()
//                .map(
//                        x ->
//                                carPoolingReservationsMapper
//                                        .toCarPoolingReservationsDTO(x))
//                .collect(Collectors.toList());
//    }

    /**
     * Get all CarPoolingReservations by userId
     * @param userId the ID of the user
     * @return a list of CarPoolingReservationsResponseDTO
     */
    public List<CarPoolingReservationsResponseDTO> getAllReservationsByUserId(Long userId) {
        return carPoolingReservationsRepository.findByUserId(userId)
                .stream()
                .map(carPoolingReservationsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    /**
     * Get a CarPoolingReservations by its ID
     * @param id the ID of the CarPoolingReservations
     * @return the CarPoolingReservationsResponseDTO if found, null otherwise
     */
    public CarPoolingReservationsResponseDTO getReservation(Long id) {
        return carPoolingReservationsMapper
                .toResponseDTO
                        (carPoolingReservationsRepository.findById(id)
                                .orElse(null));
    }
    /**
     * Create a new CarPoolingReservations
     * @param carPoolingReservations the CarPoolingReservationsResponseDTO to create
     * @return the created CarPoolingReservationsResponseDTO
     */
    public CarPoolingReservationsResponseDTO createReservation(CarPoolingReservationsCreateDTO carPoolingReservations) {
        return carPoolingReservationsMapper
                .toResponseDTO(carPoolingReservationsRepository
                        .save(carPoolingReservationsMapper
                                .toEntity(carPoolingReservations)));
    }

    /**
     * Update an existing CarPoolingReservations
     * @param id the id of the CarPoolingReservations to update
     * @return the updated CarPoolingReservationsResponseDTO
     */
    public CarPoolingReservationsResponseDTO updateReservation(Long id, CarPoolingReservationStatus status) {
        Optional<CarPoolingReservations> reservation = carPoolingReservationsRepository.findById(id);
        if (reservation.isPresent()) {
            CarPoolingReservations existingReservation = reservation.get();
            // Mettre à jour les champs de l'entité existante
            existingReservation.setStatus(status);
            // Enregistrer l'entité mise à jour
            return carPoolingReservationsMapper.toResponseDTO(carPoolingReservationsRepository.save(existingReservation));
        } else {
            return null;
        }
    }
    // Pas utilisé pour l'instant
//    /**
//     * Delete a CarPoolingReservations by its ID
//     * @param id the ID of the CarPoolingReservations to delete
//     */
//    public void deleteReservation(Long id) {
//        carPoolingReservationsRepository.deleteById(id);
//    }

    /**
     * Count the number of participants in a carpooling by its ID
     * @param carPoolingId the ID of the carpooling
     * @return the number of participants
     */
    public int countParticipantsByCarPoolingId(Long carPoolingId) {
        return carPoolingReservationsRepository.countByCarPoolingIdAndStatus(carPoolingId, CarPoolingReservationStatus.BOOKED);
    }

    /**
     * Boolean indicating if there is any available seats in the vehicle
     * @param dto the CarPoolingReservationsResponseDTO
     * @return true if there are available seats, false otherwise
     */
    public boolean noAvailableSeats(CarPoolingReservationsResponseDTO dto) {
        int availableSeats = dto.getCarPooling().getVehicle().getPlaces();
        int reservedSeats = countParticipantsByCarPoolingId(dto.getCarPooling().getId());
        return availableSeats <= reservedSeats;
    }
    /**
     * Boolean indicating if there is any available seats in the vehicle
     * @param dto the CarPoolingReservationsCreateDTO
     * @return true if there are available seats, false otherwise
     */
    public boolean noAvailableSeats(CarPoolingReservationsCreateDTO dto) {
        // Récupérer les informations du covoiturage à partir du carPoolingId
        CarPoolingResponseDto carPooling = carPoolingService.getCarPoolingById(dto.getCarPoolingId());
        int availableSeats = carPooling.getVehicle().getPlaces();
        int reservedSeats = countParticipantsByCarPoolingId(dto.getCarPoolingId());

        return availableSeats <= reservedSeats;
    }
}
