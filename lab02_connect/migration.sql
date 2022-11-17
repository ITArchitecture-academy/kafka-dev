CREATE TABLE customers
(
    id            INTEGER PRIMARY KEY,
    name          VARCHAR NOT NULL,
    price_per_mwh DOUBLE
);

CREATE TABLE wind_parks
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE wind_turbines
(
    id           INTEGER PRIMARY KEY,
    customer_id  VARCHAR NOT NULL,
    wind_park_id VARCHAR NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (wind_park_id) REFERENCES wind_parks (id)
);

INSERT INTO customers (id, name, price_per_mwh)
VALUES (1, 'Austria', 181.50),
       (2, 'Belgium', 167.42),
       (3, 'Bulgaria', 220.71),
       (4, 'Czechia', 174.90),
       (5, 'Germany', 163.89),
       (6, 'Denmark', 27.28),
       (7, 'Estonia', 152.33),
       (8, 'Spain', 154.37),
       (9, 'Finland', 7.72),
       (10, 'France', 271.94),
       (11, 'Greece', 230.00),
       (12, 'Croatia', 214.05);



INSERT INTO wind_parks (id, name)
VALUES (1, 'North Sea'),
       (2, 'Alps'),
       (3, 'South Europe'),
       (4, 'Central Europe'),
       (5, 'East Europe'),
       (6, 'West Europe');

INSERT INTO wind_turbines (id, customer_id, wind_park_id)
VALUES (0, 3, 3),
       (1, 5, 6),
       (2, 6, 6),
       (3, 3, 5),
       (4, 4, 5),
       (5, 6, 2),
       (6, 10, 4),
       (7, 6, 5),
       (8, 8, 1),
       (9, 1, 5),
       (10, 10, 6),
       (11, 2, 6),
       (12, 5, 2),
       (13, 5, 4),
       (14, 3, 5),
       (15, 5, 6),
       (16, 5, 4),
       (17, 5, 2),
       (18, 7, 3),
       (19, 5, 4),
       (20, 5, 5),
       (21, 7, 1),
       (22, 2, 4),
       (23, 3, 1),
       (24, 6, 3),
       (25, 5, 4),
       (26, 1, 4),
       (27, 5, 1),
       (28, 4, 1),
       (29, 2, 6),
       (30, 5, 5),
       (31, 4, 5),
       (32, 1, 3),
       (33, 3, 5),
       (34, 9, 3),
       (35, 5, 6),
       (36, 2, 6),
       (37, 6, 1),
       (38, 1, 2),
       (39, 5, 6),
       (40, 8, 1),
       (41, 1, 5),
       (42, 10, 2),
       (43, 3, 5),
       (44, 1, 2),
       (45, 2, 2),
       (46, 8, 3),
       (47, 7, 5),
       (48, 5, 5),
       (49, 3, 4),
       (50, 6, 3),
       (51, 5, 1),
       (52, 4, 2),
       (53, 4, 5),
       (54, 6, 6),
       (55, 3, 3),
       (56, 3, 1),
       (57, 1, 6),
       (58, 9, 5),
       (59, 10, 1),
       (60, 7, 4),
       (61, 1, 6),
       (62, 7, 2),
       (63, 8, 1),
       (64, 10, 4),
       (65, 9, 3),
       (66, 9, 2),
       (67, 9, 4),
       (68, 3, 5),
       (69, 11, 3),
       (70, 5, 4),
       (71, 8, 3),
       (72, 2, 2),
       (73, 5, 4),
       (74, 10, 3),
       (75, 2, 6),
       (76, 3, 1),
       (77, 3, 2),
       (78, 10, 3),
       (79, 1, 2),
       (80, 2, 3),
       (81, 4, 4),
       (82, 2, 3),
       (83, 7, 3),
       (84, 11, 1),
       (85, 3, 3),
       (86, 9, 6),
       (87, 11, 1),
       (88, 4, 5),
       (89, 2, 4),
       (90, 2, 6),
       (91, 4, 5),
       (92, 5, 1),
       (93, 2, 5),
       (94, 11, 1),
       (95, 2, 4),
       (96, 10, 5),
       (97, 4, 3),
       (98, 2, 1),
       (99, 11, 5);