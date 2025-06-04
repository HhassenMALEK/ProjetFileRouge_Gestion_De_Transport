-- Sites
INSERT INTO site (id, name, label, city, latX, longY) VALUES
(1, 'Site Lyon Centre', '123 Avenue Victor Hugo', 'Lyon', 45.7578, 4.8320),
 (2, 'Site Paris Sud', '45 Rue de la République', 'Paris', 48.8566, 2.3522),
 (3, 'Site Bordeaux Est', '78 Boulevard Gambetta', 'Bordeaux', 44.8378, -0.5792),
 (4, 'Site Lyon Bellecour', '14 Place Bellecour', 'Lyon', 45.7580, 4.8322),
 (5, 'Site Lille Nord', '27 Rue du Faubourg', 'Lille', 50.6292, 3.0573),
 (6, 'Site Marseille Sud', '8 Avenue Jean Jaurès', 'Marseille', 43.2965, 5.3698)
ON DUPLICATE KEY UPDATE
                     name = VALUES(name),
                    label = VALUES(label),
                     city = VALUES(city),
                     latX = VALUES(latX),
                     longY = VALUES(longY);
