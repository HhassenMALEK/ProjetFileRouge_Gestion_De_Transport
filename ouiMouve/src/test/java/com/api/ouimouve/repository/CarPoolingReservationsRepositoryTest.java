package com.api.ouimouve.repository;

import com.api.ouimouve.bo.*;
import com.api.ouimouve.enumeration.CarPoolingReservationStatus;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.enumeration.Role;
import jakarta.validation.constraints.Negative;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarPoolingReservationsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarPoolingReservationsRepository carPoolingReservationsRepository;

    private Vehicle vehicle;
    private User user1;
    private User user2;
    private CarPooling carPooling1;
    private CarPooling carPooling2;
    private Adress adress1;
    private Adress adress2;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setImmatriculation("ABC123");
        vehicle.setSeats(4);
        entityManager.persist(vehicle);

        adress1 = new Adress();
        adress1.setLabel("123 Main St");
        adress1.setCity("Paris");
        adress1.setLatX(48.8566f);
        adress1.setLongY(2.3522f);
        entityManager.persist(adress1);
        adress2 = new Adress();
        adress2.setLabel("123 Main St");
        adress2.setCity("Paris");
        adress2.setLatX(48.8566f);
        adress2.setLongY(2.3522f);
        entityManager.persist(adress2);
        user1 = new User();
        user1.setEmail("user1");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("password");
        user1.setLicenseNumber("ABC123");
        user1.setRole(Role.USER);
        entityManager.persist(user1);



        user2 = new User();
        user2.setEmail("user2");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setPassword("password");
        user2.setLicenseNumber("XYZ456");
        user2.setRole(Role.USER);
        entityManager.persist(user2);

        carPooling1 = new CarPooling();
        carPooling1.setDeparture(new Date());
        carPooling1.setArrival(new Date());
        carPooling1.setDepartureAdress(adress1);
        carPooling1.setDestinationAdress(adress2);
        carPooling1.setDistance(450);
        carPooling1.setDurationInMinutes(240);
        carPooling1.setStatus(CarPoolingStatus.BOOKING_FULL);
        carPooling1.setOrganizer(user1);
        carPooling1.setVehicle(vehicle);
        entityManager.persist(carPooling1);

        carPooling2 = new CarPooling();
        carPooling2.setDeparture(new Date());
        carPooling2.setArrival(new Date());
        carPooling2.setDepartureAdress(adress1);
        carPooling2.setDestinationAdress(adress2);carPooling2.setDistance(200);
        carPooling2.setDurationInMinutes(120);
        carPooling2.setStatus(CarPoolingStatus.BOOKING_FULL);
        carPooling2.setOrganizer(user2);
        carPooling2.setVehicle(vehicle);
        entityManager.persist(carPooling2);
    }

    @Test
    void findByUserId_ShouldReturnReservationsForGivenUser() {
        // Given
        CarPoolingReservations reservation1 = new CarPoolingReservations();
        reservation1.setUser(user1);
        reservation1.setCarPooling(carPooling1);
        reservation1.setDate(new Date());
        reservation1.setStatus(CarPoolingReservationStatus.BOOKED);
        entityManager.persist(reservation1);

        CarPoolingReservations reservation2 = new CarPoolingReservations();
        reservation2.setUser(user2); // Autre utilisateur
        reservation2.setCarPooling(carPooling1);
        reservation2.setDate(new Date());
        reservation2.setStatus(CarPoolingReservationStatus.BOOKED);
        entityManager.persist(reservation2);

        CarPoolingReservations reservation3 = new CarPoolingReservations();
        reservation3.setUser(user1); // Même utilisateur que reservation1
        reservation3.setCarPooling(carPooling2);
        reservation3.setDate(new Date());
        reservation3.setStatus(CarPoolingReservationStatus.CANCELLED);
        entityManager.persist(reservation3);

        entityManager.flush();

        // When
        List<CarPoolingReservations> foundReservations = carPoolingReservationsRepository.findByUserIdAndDateAfter(user1.getId(), new Date());

        // Then
        assertThat(foundReservations).hasSize(2);
        assertThat(foundReservations).extracting(CarPoolingReservations::getUser).containsOnly(user1);
        assertThat(foundReservations).extracting(CarPoolingReservations::getId)
                .containsExactlyInAnyOrder(reservation1.getId(), reservation3.getId());
    }

    @Test
    void countByCarPoolingIdAndStatus_ShouldReturnCorrectCount() {
        // Given
        CarPoolingReservations r1 = new CarPoolingReservations();
        r1.setCarPooling(carPooling1);
        r1.setUser(user1);
        r1.setDate(new Date());
        r1.setStatus(CarPoolingReservationStatus.BOOKED);
        entityManager.persist(r1);

        CarPoolingReservations r2 = new CarPoolingReservations();
        r2.setCarPooling(carPooling1);
        r2.setUser(user2);
        r2.setDate(new Date());
        r2.setStatus(CarPoolingReservationStatus.BOOKED);
        entityManager.persist(r2);

        CarPoolingReservations r3 = new CarPoolingReservations();
        r3.setCarPooling(carPooling1); // Même covoiturage
        r3.setUser(user1);
        r3.setDate(new Date());
        r3.setStatus(CarPoolingReservationStatus.CANCELLED); // Statut différent
        entityManager.persist(r3);

        CarPoolingReservations r4 = new CarPoolingReservations();
        r4.setCarPooling(carPooling2); // Covoiturage différent
        r4.setUser(user1);
        r4.setDate(new Date());
        r4.setStatus(CarPoolingReservationStatus.BOOKED);
        entityManager.persist(r4);

        entityManager.flush();

        // When
        int bookedCountForCarPooling1 = carPoolingReservationsRepository.countByCarPoolingIdAndStatus(carPooling1.getId(), CarPoolingReservationStatus.BOOKED);
        int cancelledCountForCarPooling1 = carPoolingReservationsRepository.countByCarPoolingIdAndStatus(carPooling1.getId(), CarPoolingReservationStatus.CANCELLED);
        int bookedCountForCarPooling2 = carPoolingReservationsRepository.countByCarPoolingIdAndStatus(carPooling2.getId(), CarPoolingReservationStatus.BOOKED);

        // Then
        assertThat(bookedCountForCarPooling1).isEqualTo(2);
        assertThat(cancelledCountForCarPooling1).isEqualTo(1);
        assertThat(bookedCountForCarPooling2).isEqualTo(1);
    }
}