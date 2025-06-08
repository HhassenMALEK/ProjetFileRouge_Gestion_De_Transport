-- Model
INSERT INTO model (id, model_name, mark, photoUrl, motor_type, category, co2, seats_model) VALUES
(1, 'C3', 'Citroën', 'https://images.pexels.com/photos/116675/pexels-photo-116675.jpeg', 'essence', 2, 110, 5),
(2, 'Clio', 'Renault', 'https://images.pexels.com/photos/244206/pexels-photo-244206.jpeg', 'essence', 2, 105, 5),
(3, 'Kangoo', 'Renault', 'https://images.pexels.com/photos/712618/pexels-photo-712618.jpeg', 'diesel', 5, 135, 7),
(4, '208', 'Peugeot', 'https://images.pexels.com/photos/2127733/pexels-photo-2127733.jpeg', 'électrique', 2, 0, 5),
(5, 'Zoe', 'Renault', 'https://images.pexels.com/photos/2127733/pexels-photo-2127733.jpeg', 'électrique', 2, 0, 5);

-- Véhicle
INSERT INTO vehicle (id, type, immatriculation, seats, status, model_id, site_id) VALUES
-- ServiceVehicle
(1, 'service', 'AA-123-BB', 5, 'ENABLED', 3, 1), -- Citroën C3 à Lyon
(2, 'service', 'CC-456-DD', 5, 'ENABLED' ,2, 1), -- Renault Clio à Lyon
(3, 'service', 'EE-789-FF', 7, 'ENABLED', 3, 2), -- Renault Kangoo à Paris
(4, 'service', 'GG-012-HH', 5, 'BOOKED', 4, 2), -- Peugeot 208 à Paris
(5, 'service', 'II-345-JJ', 5, 'DISABLED', 1, 3); -- Renault Zoe à Bordeaux


INSERT INTO vehicle (id, type, immatriculation, seats,color, description, user_id) VALUES
-- PersonnalVehicle
(6, 'personal', 'KK-678-LL', 5,  'Rouge', 'Citroen c4', 2),
(7, 'personal', 'MM-901-NN', 4,  'Bleu', 'Citroen c3', 1),
(8, 'personal', 'OO-234-PP', 5,  'Noir', 'Citroeen c5 ', 2);

