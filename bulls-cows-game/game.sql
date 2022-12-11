DROP DATABASE IF EXISTS gameDB;
CREATE DATABASE gameDB;

USE gameDB;

CREATE TABLE game(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `answer` VARCHAR(4) NOT NULL,
    `time` INT default 0,
    `finished` BOOLEAN default false
);