CREATE DATABASE IF NOT EXISTS blackjack;

USE blackjack;

CREATE TABLE IF NOT EXISTS player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    score DOUBLE DEFAULT 0.0,
    hand VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS scoreboard (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT,
    player_rank INT,
    FOREIGN KEY (player_id) REFERENCES player(id)
);