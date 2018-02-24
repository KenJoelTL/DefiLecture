-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Sam 24 Février 2018 à 20:57
-- Version du serveur :  5.7.11
-- Version de PHP :  5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `defilecture`
--

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE or REPLACE `compte` (
  `ID_COMPTE` int(10) NOT NULL,
  `ID_EQUIPE` int(10) DEFAULT NULL,
  `COURRIEL` varchar(255) NOT NULL,
  `MOT_PASSE` varchar(12) NOT NULL,
  `NOM` varchar(255) NOT NULL,
  `PRENOM` varchar(255) NOT NULL,
  `POINT` int(10) DEFAULT '0',
  `MINUTES_RESTANTES` int(10) DEFAULT '0',
  `PROGRAMME_ETUDE` varchar(255) DEFAULT NULL,
  `AVATAR` varchar(255) DEFAULT NULL,
  `PSEUDONYME` varchar(255) DEFAULT NULL,
  `ROLE` int(10) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`ID_COMPTE`, `ID_EQUIPE`, `COURRIEL`, `MOT_PASSE`, `NOM`, `PRENOM`, `POINT`, `MINUTES_RESTANTES`, `PROGRAMME_ETUDE`, `AVATAR`, `PSEUDONYME`, `ROLE`) VALUES
(1, NULL, 'admin@mail.com', 'admin', 'admin', 'Admin', 0, 0, NULL, NULL, 'admin', 4);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`ID_COMPTE`),
  ADD UNIQUE KEY `COURRIEL_UNQ` (`COURRIEL`),
  ADD UNIQUE KEY `COMPTE_UNQ` (`PSEUDONYME`),
  ADD KEY `ID_EQUIPE` (`ID_EQUIPE`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `ID_COMPTE` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `COMPTE_FK1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`) ON DELETE SET NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
