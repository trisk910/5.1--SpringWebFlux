CREATE DATABASE IF NOT EXISTS `blackjack`;
USE `blackjack`;

-- Drop tables if they already exist
DROP TABLE IF EXISTS `scoreboard`;
DROP TABLE IF EXISTS `player`;

-- Create player table
CREATE TABLE `player` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          `score` DECIMAL(7,2) NOT NULL DEFAULT '0.00',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create scoreboard table
CREATE TABLE `scoreboard` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `player_id` INT NOT NULL,
                              `score` DECIMAL(7,2) NOT NULL DEFAULT '0.00',
                              PRIMARY KEY (`id`),
                              KEY `player_id_idx` (`player_id`),
                              CONSTRAINT `fk_scoreboard_player` FOREIGN KEY (`player_id`)
                                  REFERENCES `player` (`id`)
                                  ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
