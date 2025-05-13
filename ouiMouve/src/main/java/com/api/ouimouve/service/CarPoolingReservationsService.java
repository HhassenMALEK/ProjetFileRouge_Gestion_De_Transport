package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPoolingReservations;
import com.api.ouimouve.dto.CarPoolingReservationsDTO;
import com.api.ouimouve.mapper.CarPoolingReservationsMapper;
import com.api.ouimouve.repository.CarPoolingReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarPoolingReservationsService {
    @Autowired
    private CarPoolingReservationsRepository carPoolingReservationsRepository;
    @Autowired
    private CarPoolingReservationsMapper carPoolingReservationsMapper;
    /**
     * Get all CarPoolingReservations
     * @return a list of CarPoolingReservationsDTO
     */
    public List<CarPoolingReservationsDTO> getAllReservations() {
        return carPoolingReservationsRepository.findAll()
                .stream()
                .map(
                        x ->
                                carPoolingReservationsMapper
                                        .toCarPoolingReservationsDTO(x))
                .collect(Collectors.toList());
    }

    /**
     * Get all CarPoolingReservations by userId
     * @param userId the ID of the user
     * @return a list of CarPoolingReservationsDTO
     */
//    public List<CarPoolingReservationsDTO> getAllReservationsByUserId(Long userId) {
//        return carPoolingReservationsRepository.findByUser(userId)
//                .stream()
//                .map(carPoolingReservationsMapper::toCarPoolingReservationsDTO)
//                .collect(Collectors.toList());
//    }
    /**
     * Get a CarPoolingReservations by its ID
     * @param id the ID of the CarPoolingReservations
     * @return the CarPoolingReservationsDTO if found, null otherwise
     */
    public CarPoolingReservationsDTO getReservation(Long id) {
        return carPoolingReservationsMapper
                .toCarPoolingReservationsDTO
                        (carPoolingReservationsRepository.findById(id)
                                .orElse(null));
    }
    /**
     * Create a new CarPoolingReservations
     * @param carPoolingReservations the CarPoolingReservationsDTO to create
     * @return the created CarPoolingReservationsDTO
     */
    public CarPoolingReservationsDTO createReservation(CarPoolingReservationsDTO carPoolingReservations) {
        return carPoolingReservationsMapper
                .toCarPoolingReservationsDTO(carPoolingReservationsRepository
                        .save(carPoolingReservationsMapper
                                .toCarPoolingReservations(carPoolingReservations)));
    }

    /**
     * Update an existing CarPoolingReservations
     * @param id the id of the CarPoolingReservations to update
     * @param carPoolingReservations the updated CarPoolingReservationsDTO
     * @return the updated CarPoolingReservationsDTO
     */
    public CarPoolingReservationsDTO updateReservation(Long id, CarPoolingReservationsDTO carPoolingReservations) {
        if (carPoolingReservationsRepository.existsById(id)) {
            return carPoolingReservationsMapper.toCarPoolingReservationsDTO(
                    carPoolingReservationsRepository.save(
                            carPoolingReservationsMapper.toCarPoolingReservations(carPoolingReservations)
                    )
            );
        }
        return null;
    }
    /**
     * Delete a CarPoolingReservations by its ID
     * @param id the ID of the CarPoolingReservations to delete
     */
    public void deleteReservation(Long id) {
        carPoolingReservationsRepository.deleteById(id);
    }
}
