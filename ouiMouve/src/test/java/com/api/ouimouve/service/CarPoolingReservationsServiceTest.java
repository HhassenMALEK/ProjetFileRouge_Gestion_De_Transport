package com.api.ouimouve.service;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.bo.CarPoolingReservations;
import com.api.ouimouve.bo.PersonalVehicle;
import com.api.ouimouve.bo.User;
import com.api.ouimouve.dto.CarPoolingReservationsCreateDTO;
import com.api.ouimouve.dto.CarPoolingReservationsResponseDTO;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.mapper.CarPoolingReservationsMapper;
import com.api.ouimouve.repository.CarPoolingReservationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarPoolingReservationsServiceTest {

    @Mock
    private CarPoolingReservationsRepository carPoolingReservationsRepository;

    @Mock
    private CarPoolingReservationsMapper carPoolingReservationsMapper;

    @Mock
    private CarPoolingService carPoolingService;

    @InjectMocks
    private CarPoolingReservationsService carPoolingReservationsService;

    private CarPoolingReservations reservation;
    private CarPoolingReservationsResponseDTO responseDto;
    private CarPoolingReservationsCreateDTO createDto;
    private CarPoolingResponseDto carPoolingDto;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        CarPooling carPooling = new CarPooling();
        carPooling.setId(1L);

        reservation = new CarPoolingReservations();
        reservation.setId(1L);
        reservation.setDate(new Date());
        reservation.setStatus(CarPoolingReservationStatus.BOOKED);
        reservation.setUser(user);
        reservation.setCarPooling(carPooling);

        PersonalVehicle vehicle = new PersonalVehicle();
        vehicle.setId(1L);
        vehicle.setSeats(4);
        vehicle.setUser(user);

        carPoolingDto = new CarPoolingResponseDto(); // Supposition de structure
        carPoolingDto.setId(1L);
        carPoolingDto.setVehicle(vehicle);


        responseDto = new CarPoolingReservationsResponseDTO();
        responseDto.setId(1L);
        responseDto.setDate(reservation.getDate());
        responseDto.setStatus(CarPoolingReservationStatus.BOOKED);
        responseDto.setCarPooling(carPoolingDto);
        responseDto.setParticipantCount(1);


        createDto = new CarPoolingReservationsCreateDTO();
        createDto.setCarPoolingId(1L);
        createDto.setUserId(1L);
        createDto.setDate(new Date());
    }

    @Test
    void getAllReservationsByUserId_ShouldReturnListOfResponseDtos() {
        // Given
        List<CarPoolingReservations> reservations = Collections.singletonList(reservation);
        when(carPoolingReservationsRepository.findByUserId(1L)).thenReturn(reservations);
        when(carPoolingReservationsMapper.toResponseDTO(any(CarPoolingReservations.class))).thenReturn(responseDto);

        // When
        List<CarPoolingReservationsResponseDTO> result = carPoolingReservationsService.getAllReservationsByUserId(1L);

        // Then
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0)).isEqualTo(responseDto);
        verify(carPoolingReservationsRepository, times(1)).findByUserId(1L);
        verify(carPoolingReservationsMapper, times(1)).toResponseDTO(reservation);
    }

    @Test
    void getReservation_WithExistingId_ShouldReturnResponseDto() {
        // Given
        when(carPoolingReservationsRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(carPoolingReservationsMapper.toResponseDTO(reservation)).thenReturn(responseDto);

        // When
        CarPoolingReservationsResponseDTO result = carPoolingReservationsService.getReservation(1L);

        // Then
        assertThat(result).isNotNull().isEqualTo(responseDto);
        verify(carPoolingReservationsRepository, times(1)).findById(1L);
        verify(carPoolingReservationsMapper, times(1)).toResponseDTO(reservation);
    }

    @Test
    void getReservation_WithNonExistingId_ShouldReturnNull() {
        // Given
        when(carPoolingReservationsRepository.findById(99L)).thenReturn(Optional.empty());
        when(carPoolingReservationsMapper.toResponseDTO(null)).thenReturn(null);


        // When
        CarPoolingReservationsResponseDTO result = carPoolingReservationsService.getReservation(99L);

        // Then
        assertThat(result).isNull();
        verify(carPoolingReservationsRepository, times(1)).findById(99L);
        verify(carPoolingReservationsMapper, times(1)).toResponseDTO(null);
    }

    @Test
    void createReservation_ShouldReturnCreatedResponseDto() {
        // Given
        when(carPoolingReservationsMapper.toEntity(createDto)).thenReturn(reservation);
        when(carPoolingReservationsRepository.save(reservation)).thenReturn(reservation);
        when(carPoolingReservationsMapper.toResponseDTO(reservation)).thenReturn(responseDto);

        // When
        CarPoolingReservationsResponseDTO result = carPoolingReservationsService.createReservation(createDto);

        // Then
        assertThat(result).isNotNull().isEqualTo(responseDto);
        verify(carPoolingReservationsMapper, times(1)).toEntity(createDto);
        verify(carPoolingReservationsRepository, times(1)).save(reservation);
        verify(carPoolingReservationsMapper, times(1)).toResponseDTO(reservation);
    }

    @Test
    void updateReservation_WithExistingId_ShouldReturnUpdatedResponseDto() {
        // Given
        CarPoolingReservations existingReservation = new CarPoolingReservations();
        existingReservation.setId(1L);
        existingReservation.setStatus(CarPoolingReservationStatus.BOOKED);

        CarPoolingReservations updatedReservation = new CarPoolingReservations();
        updatedReservation.setId(1L);
        updatedReservation.setStatus(CarPoolingReservationStatus.CANCELLED);


        CarPoolingReservationsResponseDTO updatedDto = new CarPoolingReservationsResponseDTO();
        updatedDto.setId(1L);
        updatedDto.setStatus(CarPoolingReservationStatus.CANCELLED);

        when(carPoolingReservationsRepository.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(carPoolingReservationsRepository.save(any(CarPoolingReservations.class))).thenReturn(updatedReservation);
        when(carPoolingReservationsMapper.toResponseDTO(updatedReservation)).thenReturn(updatedDto);

        // When
        CarPoolingReservationsResponseDTO result = carPoolingReservationsService.updateReservation(1L, CarPoolingReservationStatus.CANCELLED);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(CarPoolingReservationStatus.CANCELLED);
        verify(carPoolingReservationsRepository, times(1)).findById(1L);
        verify(carPoolingReservationsRepository, times(1)).save(argThat(savedRes -> savedRes.getStatus() == CarPoolingReservationStatus.CANCELLED));
        verify(carPoolingReservationsMapper, times(1)).toResponseDTO(updatedReservation);
    }

    @Test
    void updateReservation_WithNonExistingId_ShouldReturnNull() {
        // Given
        when(carPoolingReservationsRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        CarPoolingReservationsResponseDTO result = carPoolingReservationsService.updateReservation(99L, CarPoolingReservationStatus.CANCELLED);

        // Then
        assertThat(result).isNull();
        verify(carPoolingReservationsRepository, times(1)).findById(99L);
        verify(carPoolingReservationsRepository, never()).save(any());
        verify(carPoolingReservationsMapper, never()).toResponseDTO(any());
    }

    @Test
    void countParticipantsByCarPoolingId_ShouldReturnCount() {
        // Given
        when(carPoolingReservationsRepository.countByCarPoolingIdAndStatus(1L, CarPoolingReservationStatus.BOOKED)).thenReturn(5);

        // When
        int count = carPoolingReservationsService.countParticipantsByCarPoolingId(1L);

        // Then
        assertThat(count).isEqualTo(5);
        verify(carPoolingReservationsRepository, times(1)).countByCarPoolingIdAndStatus(1L, CarPoolingReservationStatus.BOOKED);
    }

    @Test
    void noAvailableSeats_WithResponseDTO_WhenSeatsAvailable_ShouldReturnFalse() {
        // Given
        responseDto.getCarPooling().getVehicle().setSeats(5); // 5 sièges au total
        // countParticipantsByCarPoolingId sera appelé, mockons son comportement sous-jacent
        when(carPoolingReservationsRepository.countByCarPoolingIdAndStatus(responseDto.getCarPooling().getId(), CarPoolingReservationStatus.BOOKED)).thenReturn(3); // 3 sièges réservés

        // When
        boolean result = carPoolingReservationsService.noAvailableSeats(responseDto);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void noAvailableSeats_WithResponseDTO_WhenNoSeatsAvailable_ShouldReturnTrue() {
        // Given
        responseDto.getCarPooling().getVehicle().setSeats(3); // 3 sièges au total
        when(carPoolingReservationsRepository.countByCarPoolingIdAndStatus(responseDto.getCarPooling().getId(), CarPoolingReservationStatus.BOOKED)).thenReturn(3); // 3 sièges réservés

        // When
        boolean result = carPoolingReservationsService.noAvailableSeats(responseDto);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void noAvailableSeats_WithResponseDTO_WhenMoreReservedThanAvailable_ShouldReturnTrue() {
        // Given
        responseDto.getCarPooling().getVehicle().setSeats(2); // 2 sièges au total
        when(carPoolingReservationsRepository.countByCarPoolingIdAndStatus(responseDto.getCarPooling().getId(), CarPoolingReservationStatus.BOOKED)).thenReturn(3); // 3 sièges réservés

        // When
        boolean result = carPoolingReservationsService.noAvailableSeats(responseDto);

        // Then
        assertThat(result).isTrue();
    }


    @Test
    void noAvailableSeats_WithCreateDTO_WhenSeatsAvailable_ShouldReturnFalse() {
        // Given
        createDto.setCarPoolingId(1L);
        carPoolingDto.getVehicle().setSeats(5); // 5 sièges au total
        when(carPoolingService.getCarPoolingById(1L)).thenReturn(carPoolingDto);
        when(carPoolingReservationsRepository.countByCarPoolingIdAndStatus(1L, CarPoolingReservationStatus.BOOKED)).thenReturn(3); // 3 sièges réservés

        // When
        boolean result = carPoolingReservationsService.noAvailableSeats(createDto);

        // Then
        assertThat(result).isFalse();
        verify(carPoolingService, times(1)).getCarPoolingById(1L);
    }

    @Test
    void noAvailableSeats_WithCreateDTO_WhenNoSeatsAvailable_ShouldReturnTrue() {
        // Given
        createDto.setCarPoolingId(1L);
        carPoolingDto.getVehicle().setSeats(3); // 3 sièges au total
        when(carPoolingService.getCarPoolingById(1L)).thenReturn(carPoolingDto);
        when(carPoolingReservationsRepository.countByCarPoolingIdAndStatus(1L, CarPoolingReservationStatus.BOOKED)).thenReturn(3); // 3 sièges réservés

        // When
        boolean result = carPoolingReservationsService.noAvailableSeats(createDto);

        // Then
        assertThat(result).isTrue();
        verify(carPoolingService, times(1)).getCarPoolingById(1L);
    }

    @Test
    void noAvailableSeats_WithCreateDTO_WhenMoreReservedThanAvailable_ShouldReturnTrue() {
        // Given
        createDto.setCarPoolingId(1L);
        carPoolingDto.getVehicle().setSeats(2); // 2 sièges au total
        when(carPoolingService.getCarPoolingById(1L)).thenReturn(carPoolingDto);
        when(carPoolingReservationsRepository.countByCarPoolingIdAndStatus(1L, CarPoolingReservationStatus.BOOKED)).thenReturn(3); // 3 sièges réservés

        // When
        boolean result = carPoolingReservationsService.noAvailableSeats(createDto);

        // Then
        assertThat(result).isTrue();
        verify(carPoolingService, times(1)).getCarPoolingById(1L);
    }
}