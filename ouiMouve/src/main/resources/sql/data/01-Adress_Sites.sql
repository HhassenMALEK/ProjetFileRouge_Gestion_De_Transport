-- Adresses
INSERT INTO adress (id, label, city, latX, longY) VALUES
                                                      (1, '123 Avenue Victor Hugo', 'Lyon', 45.7578, 4.8320),
                                                      (2, '45 Rue de la République', 'Paris', 48.8566, 2.3522),
                                                      (3, '78 Boulevard Gambetta', 'Bordeaux', 44.8378, -0.5792),
                                                      (4, '14 Place Bellecour', 'Lyon', 45.7580, 4.8322),
                                                      (5, '27 Rue du Faubourg', 'Lille', 50.6292, 3.0573),
                                                      (6, '8 Avenue Jean Jaurès', 'Marseille', 43.2965, 5.3698)
ON DUPLICATE KEY UPDATE
                     label = VALUES(label),
                     city = VALUES(city),
                     latX = VALUES(latX),
                     longY = VALUES(longY);

-- Sites
INSERT INTO site (id, name, adress_id) VALUES
                                           (1, 'Site Lyon Centre', 1),
                                           (2, 'Site Paris Sud', 2),
                                           (3, 'Site Bordeaux Est', 3)
ON DUPLICATE KEY UPDATE
                     name = VALUES(name),
                     adress_id = VALUES(adress_id);