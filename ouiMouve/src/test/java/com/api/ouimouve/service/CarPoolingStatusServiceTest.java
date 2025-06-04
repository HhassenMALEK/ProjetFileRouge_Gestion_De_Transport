package com.api.ouimouve.service;

import com.api.ouimouve.bo.*;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.enumeration.Role;
import com.api.ouimouve.repository.CarPoolingRepository;
import com.api.ouimouve.repository.CarPoolingReservationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarPoolingStatusServiceTest {

    @Mock
    private CarPoolingRepository carPoolingRepository;

    @Mock
    private CarPoolingReservationsRepository reservationsRepository;

    @InjectMocks
    private CarPoolingStatusService carPoolingStatusServiceService;

    @Test
    void closeExpiredCarpoolings() {

        // given : des données de test
        CarPooling carPooling = new CarPooling();
        carPooling.setId(20L);
        carPooling.setDeparture(Date.from(LocalDateTime.now().minusDays(2)
                .atZone(ZoneId.systemDefault()).toInstant()));
        carPooling.setDurationInMinutes(60);
        carPooling.setStatus(CarPoolingStatus.BOOKING_OPEN);
        carPooling.setDurationInMinutes(150);
        carPooling.setDistance(150);

        // Mocking the departure site
        Site departureSite = new Site();
        departureSite.setId(1L);
        departureSite.setName("Site Lyon Centre");
        departureSite.setLabel("123 Avenue Victor Hugo");
        departureSite.setCity("Lyon");
        departureSite.setLatX(45.7578F);
        departureSite.setLongY(4.8320F);
        carPooling.setDepartureSite(departureSite);

        // Mocking the destination site
        Site destinationSite = new Site();
        destinationSite.setId(4L);
        destinationSite.setName("Site Lyon Bellecour");
        destinationSite.setLabel("14 Place Bellecour");
        destinationSite.setCity("Lyon");
        destinationSite.setLatX(45.7580F);
        destinationSite.setLongY(4.8322F);
        carPooling.setDestinationSite(destinationSite);
        //Setting Vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setImmatriculation("AB-123-CD");
        vehicle.setSeats(4);
        vehicle.setCarPoolings(null);
        carPooling.setVehicle(vehicle);
        // Setting Organizer
        User user = new User();
        user.setId(1L);
        user.setFirstName("Joe");
        user.setLastName("Br");
        user.setEmail("jbr@gmail.com");
        user.setPassword("$2a$10$e0N1z5Zb3f8j5k1y7Q6uUu9F4d3h5l5m5l5m5l5m5l5m5l5m5l5m");
        user.setRole(Role.USER);
        user.setLicenseNumber("A12345678");
        carPooling.setOrganizer(user);

        CarPoolingReservations res1 = new CarPoolingReservations();
        res1.setDate(Date.from(LocalDateTime.now().minusDays(3)
                .atZone(ZoneId.systemDefault()).toInstant()));
        res1.setStatus(CarPoolingReservationStatus.BOOKED);
        res1.setCarPooling(carPooling);
        carPooling.setReservations(List.of(res1));

        // Mock repository behavior
        when(carPoolingRepository.findAllNonFinishedNonCancelled()).thenReturn(List.of(carPooling));
        when(carPoolingRepository.save(carPooling)).thenReturn(carPooling);
        when(reservationsRepository.saveAll(List.of(res1))).thenReturn(List.of(res1));

        // When
        carPoolingStatusServiceService.closeExpiredCarpoolings();

        // Then
        assertEquals(CarPoolingStatus.FINISHED, carPooling.getStatus());
        assertEquals(CarPoolingReservationStatus.FINISHED, res1.getStatus());

        verify(carPoolingRepository).save(carPooling);
        verify(reservationsRepository).saveAll(anyList());

    }

}