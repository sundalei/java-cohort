DROP DATABASE IF EXISTS todoDB;
CREATE DATABASE todoDB;

USE todoDB;

CREATE TABLE todo(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `todo` VARCHAR(50) NOT NULL,
    `note` VARCHAR(100),
    `finished` BOOLEAN NOT NULL DEFAULT false
);