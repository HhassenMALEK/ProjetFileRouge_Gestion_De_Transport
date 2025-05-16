-- Model
INSERT INTO model (id, model_name, mark, photoUrl, motor_type, category, co2, places_model) VALUES
(1, 'C3', 'Citroën', 'https://example.com/c3.jpg', 'essence', 2, 110, 5),
(2, 'Clio', 'Renault', 'https://example.com/clio.jpg', 'essence', 2, 105, 5),
(3, 'Kangoo', 'Renault', 'https://example.com/kangoo.jpg', 'diesel', 5, 135, 7),
(4, '208', 'Peugeot', 'https://example.com/208.jpg', 'électrique', 2, 0, 5),
(5, 'Zoe', 'Renault', 'https://example.com/zoe.jpg', 'électrique', 2, 0, 5);

-- Véhicle
INSERT INTO vehicle (id, type, immatriculation, places, status, model_id, site_id) VALUES
-- ServiceVehicle
(1, 'service', 'AA-123-BB', 5, 'ENABLED', 1, 1), -- Citroën C3 à Lyon
(2, 'service', 'CC-456-DD', 5, 'ENABLED' ,2, 1), -- Renault Clio à Lyon
(3, 'service', 'EE-789-FF', 7, 'ENABLED', 3, 2), -- Renault Kangoo à Paris
(4, 'service', 'GG-012-HH', 5, 'BOOKED', 4, 2), -- Peugeot 208 à Paris
(5, 'service', 'II-345-JJ', 5, 'DISABLED', 5, 3); -- Renault Zoe à Bordeaux


INSERT INTO vehicle (id, type, immatriculation, places, status,color, description, user_id) VALUES
-- PersonnalVehicle
(6, 'personal', 'KK-678-LL', 5, 'ENABLED', 'Rouge', 'Berline familiale, très confortable', 2),
(7, 'personal', 'MM-901-NN', 4, 'ENABLED', 'Bleu', 'Citadine compacte', 3),
(8, 'personal', 'OO-234-PP', 5, 'ENABLED', 'Noir', 'SUV spacieux', 4);

