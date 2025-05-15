-- Model
INSERT INTO model (id, model_name, mark, photoUrl, motor_type, category, co2, places_model) VALUES
(1, 'C3', 'Citroën', 'https://example.com/c3.jpg', 'essence', 2, 110, 5),
(2, 'Clio', 'Renault', 'https://example.com/clio.jpg', 'essence', 2, 105, 5),
(3, 'Kangoo', 'Renault', 'https://example.com/kangoo.jpg', 'diesel', 5, 135, 7),
(4, '208', 'Peugeot', 'https://example.com/208.jpg', 'électrique', 2, 0, 5),
(5, 'Zoe', 'Renault', 'https://example.com/zoe.jpg', 'électrique', 2, 0, 5);

-- Véhicle
INSERT INTO vehicle (id, type, immatriculation, places, status) VALUES
-- ServiceVehicle
(1, 'service', 'AA-123-BB', 5, ENABLED),
(2, 'service', 'CC-456-DD', 5, ENABLED),
(3, 'service', 'EE-789-FF', 7, ENABLED),
(4, 'service', 'GG-012-HH', 5, BOOKED),
(5, 'service', 'II-345-JJ', 5, DISABLED),
-- PersonnalVehicle
(6, 'personal', 'KK-678-LL', 5, ENABLED),
(7, 'personal', 'MM-901-NN', 4, ENABLED),
(8, 'personal', 'OO-234-PP', 5, ENABLED);

-- ServiceVehicle Configuration
INSERT INTO service_vehicle (id, model_id, site_id) VALUES
(1, 1, 1), -- Citroën C3 à Lyon
(2, 2, 1), -- Renault Clio à Lyon
(3, 3, 2), -- Renault Kangoo à Paris
(4, 4, 2), -- Peugeot 208 à Paris
(5, 5, 3); -- Renault Zoe à Bordeaux

-- PersonnalVehicle Configuration
INSERT INTO personal_vehicle (id, color, description, user_id) VALUES
(6, 'Rouge', 'Berline familiale, très confortable', 2),
(7, 'Bleu', 'Citadine compacte', 3),
(8, 'Noir', 'SUV spacieux', 4);