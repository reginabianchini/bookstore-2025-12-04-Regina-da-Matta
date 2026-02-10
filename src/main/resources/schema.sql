CREATE TABLE IF NOT EXISTS foundation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255),
    string_count INT,
    price DOUBLE
);
CREATE TABLE IF NOT EXISTS lipstick (
     id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255),
    string_count INT,
    price DOUBLE
    );