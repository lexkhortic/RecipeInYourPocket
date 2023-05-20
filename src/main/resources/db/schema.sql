CREATE TABLE IF NOT EXISTS owners(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_login VARCHAR(50) NOT NULL UNIQUE,
    owner_password VARCHAR(50) NOT NULL,
    owner_role VARCHAR(50) NOT NULL,
    name_company VARCHAR(50) NOT NULL UNIQUE,
    phone_company VARCHAR(50) NOT NULL UNIQUE,
    link_company VARCHAR(50) NOT NULL UNIQUE,
    INDEX (owner_login, owner_password)
);

CREATE TABLE IF NOT EXISTS pharmacies(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(30) NOT NULL,
    address VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL UNIQUE,
    latitude DECIMAL(10,6) NOT NULL,
    longitude DECIMAL(10,6) NOT NULL
);

CREATE TABLE IF NOT EXISTS medicines(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    country VARCHAR(80) NOT NULL,
    count INT NOT NULL,
    price DECIMAL(8,2) NOT NULL,
    CHECK ( count >= 0 AND price > 0)
);

CREATE TABLE IF NOT EXISTS owners_pharmacies(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    pharmacy_id INT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES owners(id),
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacies(id)
);

CREATE TABLE IF NOT EXISTS pharmacies_medicines(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pharmacy_id INT NOT NULL,
    medicine_id INT NOT NULL,
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacies(id),
    FOREIGN KEY (medicine_id) REFERENCES medicines(id)
);










