-- Réservation
INSERT INTO vehicle_reservation (id, user_id, start, end, status, service_vehicle_id) VALUES
(1, 1, '2023-11-20 08:00:00', '2023-11-20 18:00:00', 'BOOKED', 1),
(2, 1, '2023-11-21 09:00:00', '2023-11-22 17:00:00', 'BOOKED', 2),
(3, 2, '2023-11-25 10:00:00', '2023-11-25 16:00:00', 'BOOKED', 3),
(4, 2, '2023-12-01 08:00:00', '2023-12-01 19:00:00', 'BOOKED', 4);

-- CarPooling
INSERT INTO car_pooling (id, departure, status, duration_in_minutes, distance, departure_site_id, destination_site_id, vehicle_id, organizer_id) VALUES
(1, '2023-11-22 08:00:00', 'BOOKING_OPEN', 90, 80, 1, 2, 6, 2),
(2, '2023-11-23 17:30:00', 'BOOKING_OPEN', 90, 80, 2, 1, 6, 2),
(3, '2023-11-24 07:45:00', 'BOOKING_FULL', 45, 30, 4, 5, 7, 2),
(4, '2024-12-24 18:45:00', 'BOOKING_FULL', 120, 140, 2, 5, 7, 2),
(5, '2025-11-24 07:45:00', 'BOOKING_FULL', 45, 30, 4, 5, 7, 2),
(6, '2025-06-04 12:00:00', 'BOOKING_FULL', 45, 30, 4, 5, 7, 2);

-- CarPoolingReservation
INSERT INTO car_pooling_reservations (id, date, status, user_id, carpooling_id) VALUES
(1, '2023-11-15 14:25:00', 'BOOKED', 1, 1),
(2, '2023-11-15 16:42:00', 'BOOKED', 1, 1),
(3, '2023-11-16 09:12:00', 'BOOKED', 2, 2),
(4, '2023-11-16 11:05:00', 'BOOKED', 2, 3),
(5, '2023-11-16 13:30:00', 'BOOKED', 2, 4),
(6, '2024-11-16 13:30:00', 'FINISHED',2, 4),
(7, '2025-11-16 13:30:00', 'BOOKED',2, 5),
(8, '2025-07-16 13:30:00', 'BOOKED', 2, 5),
(9, '2025-05-16 13:30:00', 'CANCELLED', 2, 5),
(10,'2020-11-16 13:30:00', 'BOOKED', 2, 6);

