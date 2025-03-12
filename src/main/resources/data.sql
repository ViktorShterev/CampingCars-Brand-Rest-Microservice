CREATE TABLE IF NOT EXISTS brands
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL  UNIQUE
);
INSERT INTO brands (id, name)
VALUES (1, 'Weinsberg'),
       (2, 'Hobby'),
       (3, 'LMC'),
       (4, 'Polar'),
       (5, 'ABI'),
       (6, 'Fendt'),
       (7, 'Knaus'),
       (8, 'Keystone'),
       (9, 'Rapido'),
       (10, 'Adria'),
       (11, 'Kabe'),
       (12, 'Benimar'),
       (13, 'Carthago'),
       (14, 'Buerstner'),
       (15, 'Hymer'),
       (16, 'Dethleffs');

/*Populating models table*/
CREATE TABLE IF NOT EXISTS model_ids
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    value INT UNIQUE NOT NULL,
    brand_id INT
);
INSERT INTO model_ids (id, value, brand_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 4),
       (5, 5, 5),
       (6, 6, 6),
       (7, 7, 7),
       (8, 8, 8),
       (9, 9, 9),
       (10, 10, 10),
       (11, 11, 11),
       (12, 12, 12),
       (13, 13, 13),
       (14, 14, 14),
       (15, 15, 15),
       (16, 16, 16);