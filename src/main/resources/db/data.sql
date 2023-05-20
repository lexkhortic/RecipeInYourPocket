INSERT INTO owners(id, owner_login, owner_password, owner_role, name_company, phone_company, link_company)
VALUES (1, 'lexkhortic', '123', 'OWNER', 'Белфармация', '+375448968514', 'belpharmacy.by'),
       (2, 'dimdim', '333', 'OWNER', '100 Аптек', '+375296965625', 'mad.by');

INSERT INTO pharmacies(id, city, address, title, phone, latitude, longitude)
VALUES (1, 'Минск', 'Восточная улица, 46', 'Аптека №65', '+375445915514', 53.935988, 27.596776),
       (2, 'Минск', 'улица Максима Богдановича, 143', 'Планета здоровья', '+375259412121', 53.926098, 27.570947 ),
       (3, 'Минск', 'улица Чкалова, 17', 'Аптека №8', '+375335692325', 53.876253, 27.535108);

INSERT INTO owners_pharmacies(owner_id, pharmacy_id)
VALUES (1, 1),
       (2, 2),
       (1, 3);

INSERT INTO medicines(id, name, country, count, price)
VALUES (1,'Парацетамол', 'Беларусь', 10, 5.20),
       (2,'Парацетамол', 'Россия', 20, 4.20),
       (3,'Авамис', 'Бельгия', 5, 35.21),
       (4,'Уголь активированный', 'Беларусь', 100, 0.78),
       (5,'Капсикам', 'Украина', 33, 11.55),
       (6,'Тридерм', 'Бельгия', 14, 25.33),
       (7,'Ксилин', 'Беларусь', 50, 4.67),
       (8,'Диприлиф', 'Великобритания', 6, 12.25);

INSERT INTO pharmacies_medicines(pharmacy_id, medicine_id)
VALUES (1, 1),
       (2, 2),
       (1, 3),
       (2, 4),
       (1, 5),
       (2, 6),
       (1, 7),
       (2, 8);








