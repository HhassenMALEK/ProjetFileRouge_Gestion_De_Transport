package com.api.ouimouve.service;

import com.api.ouimouve.dto.CarPoolingDto;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.mapper.CarPoolingMapper;
import com.api.ouimouve.repository.CarPoolingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class CarPoolingService {
    @Autowired
    private CarPoolingRepository carPoolingRepository;
    @Autowired
    CarPoolingMapper carPoolingMapper;

    // Add methods to handle CRUD operations for CarPooling entities
    /**
     * Retrieves all carpoolings.
     *
     * @return a list of CarPoolingDto
     */
    public List<CarPoolingDto> getAllCarPoolings() {
        return carPoolingRepository.findAll().stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a carpooling by its ID.
     *
     * @param id the ID of the carpooling
     * @return the corresponding CarPoolingDto if found, otherwise null
     */
    public CarPoolingDto getCarPoolingById(Long id) {
        return carPoolingRepository.findById(id)
                .map(carPoolingMapper::toCarPoolingDto)
                .orElse(null);
    }

    /**
     * Retrieves all carpoolings with a specific status.
     *
     * @param status the status to filter by
     * @return a list of CarPoolingDto
     */
    public List<CarPoolingDto> getCarPoolingsByStatus(CarPoolingStatus status) {
        return carPoolingRepository.findByStatus(status).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
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

    /**
     * Retrieves carpoolings by status and with departure after the given date.
     *
     * @param status the status to filter by
     * @param date the minimum departure date
     * @return a list of CarPoolingDto
     */
    public List<CarPoolingDto> getCarPoolingsByStatusAndDate(CarPoolingStatus status, Date date) {
        return carPoolingRepository.findByStatusAndDepartureAfter(status, date).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves carpoolings by status ordered by departure date ascending.
     *
     * @param status the status to filter by
     * @return a list of CarPoolingDto
     */
    public List<CarPoolingDto> getCarPoolingsByStatusOrdered(CarPoolingStatus status) {
        return carPoolingRepository.findByStatusOrderByDepartureAsc(status).stream()
                .map(carPoolingMapper::toCarPoolingDto)
                .collect(Collectors.toList());
    }

    /**
     * creates a new carpooling.
     *
     * @param dto the CarPoolingDto to create
     * the created CarPoolingDto
     */
    public CarPoolingDto createCarPooling(CarPoolingDto dto) {
        return carPoolingMapper.toCarPoolingDto(carPoolingRepository.save(carPoolingMapper.toEntity(dto)));
    }

    /**
     * Updates an existing carpooling.
     *
     * @param id the ID of the carpooling to update
     * @param dto the CarPoolingDto with updated information
     * @return the updated CarPoolingDto
     */
    public CarPoolingDto updateCarPooling(Long id, CarPoolingDto dto) {
        return carPoolingRepository.findById(id)
                .map(carPooling -> {
                    carPooling.setDeparture(dto.getDeparture());
                    carPooling.setArrival(dto.getArrival());
                    carPooling.setStatus(dto.getStatus());
                    return carPoolingMapper.toCarPoolingDto(carPoolingRepository.save(carPooling));
                })
                .orElseThrow(() -> new RessourceNotFoundException("Carpooling not found with id: " + id));
    }

    /**
     * Deletes a carpooling by ID.
     *
     * @param id the ID of the carpooling to delete
     */
    public void deleteCarPooling(Long id) {
        if (!carPoolingRepository.existsById(id)) {
            throw new RessourceNotFoundException("Carpooling not found with id: " + id);
        }
        carPoolingRepository.deleteById(id);
    }

}
