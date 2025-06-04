package com.api.ouimouve.service;

import com.api.ouimouve.bo.*;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.enumeration.CarPoolingStatus;
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
        carPooling.setId(1L);
        carPooling.setDeparture(Date.from(LocalDateTime.now().minusDays(2)
                .atZone(ZoneId.systemDefault()).toInstant()));
        carPooling.setDurationInMinutes(60);
        carPooling.setStatus(CarPoolingStatus.BOOKING_OPEN);
        carPooling.setDurationInMinutes(150);
        carPooling.setDistance(150);
        carPooling.setDepartureSite(new Site (1L, "Site Lyon Centre", "123 Avenue Victor Hugo", "Lyon", 45.7578F, 4.8320F));
        carPooling.setDestinationSite(new Site(4L, "Site Lyon Bellecour", "14 Place Bellecour", "Lyon", 45.7580F, 4.8322F));
        carPooling.setVehicle(new Vehicle(1L, "AB-123-CD", 4, null));
        carPooling.setOrganizer(new User());
        CarPoolingReservations res1 = new CarPoolingReservations();
        res1.setStatus(CarPoolingReservationStatus.BOOKED);
        res1.setCarPooling(carPooling);
        carPooling.setReservations(List.of(res1));

        when(carPoolingRepository.findAll()).thenReturn(List.of(carPooling));

        // when : on exécute la méthode
        carPoolingStatusServiceService.closeExpiredCarpoolings();
//        // then : on vérifie les effets attendus
//        assertEquals(CarPoolingStatus.FINISHED, carPooling.getStatus());
//        assertEquals(CarPoolingReservationStatus.FINISHED, res1.getStatus());

        // Then
        //CarPooling updated = carPoolingRepository.findById(carPooling.getId()).orElseThrow();
        //assertEquals(CarPoolingStatus.FINISHED, updated.getStatus());

        CarPoolingReservations updatedRes = reservationsRepository.findById(res1.getId()).orElseThrow();
        assertEquals(CarPoolingReservationStatus.FINISHED, updatedRes.getStatus());

        verify(carPoolingRepository).save(carPooling);
        verify(reservationsRepository).save(res1);
    }

}