-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 15 avr. 2026 à 15:08
-- Version du serveur : 8.4.7
-- Version de PHP : 8.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `hotel_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `chambre`
--

DROP TABLE IF EXISTS `chambre`;
CREATE TABLE IF NOT EXISTS `chambre` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `numero` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    `prix_nuit` decimal(10,2) NOT NULL,
    `disponible` tinyint(1) NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    UNIQUE KEY `numero` (`numero`)
    ) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `chambre`
--

INSERT INTO `chambre` (`id`, `numero`, `type`, `prix_nuit`, `disponible`) VALUES
                                                                              (1, '101', 'Simple', 250.00, 1),
                                                                              (2, '102', 'Double', 400.00, 1),
                                                                              (3, '201', 'Suite', 800.00, 1),
                                                                              (4, '202', 'Double', 450.00, 0);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `nom` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `prenom` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `telephone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `telephone`) VALUES
                                                              (1, 'Dupont', 'Jean', '0600000001'),
                                                              (2, 'Martin', 'Sophie', '0600000002'),
                                                              (3, 'Bernard', 'Ali', '0600000003');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
                                             `id` int NOT NULL AUTO_INCREMENT,
                                             `id_client` int NOT NULL,
                                             `id_chambre` int NOT NULL,
                                             `date_debut` date NOT NULL,
                                             `date_fin` date NOT NULL,
                                             PRIMARY KEY (`id`),
    KEY `fk_reservation_client` (`id_client`),
    KEY `fk_reservation_chambre` (`id_chambre`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
