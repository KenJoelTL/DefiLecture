--    This file is part of DefiLecture.
--
--    DefiLecture is free software: you can redistribute it and/or modify
--    it under the terms of the GNU General Public License as published by
--    the Free Software Foundation, either version 3 of the License, or
--    (at your option) any later version.
--
--    DefiLecture is distributed in the hope that it will be useful,
--    but WITHOUT ANY WARRANTY; without even the implied warranty of
--    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--    GNU General Public License for more details.
--
--    You should have received a copy of the GNU General Public License
--    along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.


-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Mar 03 Avril 2018 à 00:33
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

DROP USER IF EXISTS defilecture;
DROP DATABASE IF EXISTS defilecture;
CREATE DATABASE defilecture;

USE defilecture;

CREATE USER defilecture IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES on defilecture.* to 'defilecture'@'%';

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

DROP TABLE IF EXISTS `compte`;
CREATE TABLE `compte` (
  `ID_COMPTE` int(10) NOT NULL,
  `ID_EQUIPE` int(10) DEFAULT NULL,
  `COURRIEL` varchar(255) NOT NULL,
  `MOT_PASSE` varchar(64) NOT NULL,
  `NOM` varchar(255) NOT NULL,
  `PRENOM` varchar(255) NOT NULL,
  `POINT` int(10) DEFAULT '0',
  `MINUTES_RESTANTES` int(10) DEFAULT '0',
  `PROGRAMME_ETUDE` varchar(255) DEFAULT NULL,
  `AVATAR` varchar(255) DEFAULT '/images/avatars/avatarCompte_defaut.png',
  `PSEUDONYME` varchar(255) DEFAULT NULL,
  `ROLE` int(10) NOT NULL DEFAULT '1',
  `DEVENIR_CAPITAINE` int(3) NOT NULL DEFAULT '0',
  `SEL` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Vider la table avant d'insérer `compte`
--

TRUNCATE TABLE `compte`;
--
-- Contenu de la table `compte`
--

INSERT INTO `compte` 
(`ID_COMPTE`, 
`ID_EQUIPE`, 
`COURRIEL`, 
`MOT_PASSE`, 
`NOM`, 
`PRENOM`, 
`POINT`, 
`MINUTES_RESTANTES`, 
`PROGRAMME_ETUDE`, 
`PSEUDONYME`, 
`ROLE`, 
`DEVENIR_CAPITAINE`,
`SEL`) 
VALUES
(1, 
NULL, 
'admin@mail.com', 
'd7306b69d6144db293e52c0bbf1f3fa3210b42ed8e122aaaf2dad7db0aa4ac93',
'admin', 
'Admin', 
0, 
0, 
'', 
'admin', 
4, 
0,
'ungraindeselpourtest');

-- --------------------------------------------------------

--
-- Structure de la table `defi`
--

DROP TABLE IF EXISTS `defi`;
CREATE TABLE `defi` (
  `ID_DEFI` int(10) NOT NULL,
  `ID_COMPTE` int(10) DEFAULT NULL,
  `NOM` varchar(255) NOT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `DATE_DEBUT` datetime(2) NOT NULL,
  `DATE_FIN` datetime(2) NOT NULL,
  `QUESTION` varchar(1024) DEFAULT NULL,
  `CHOIX_REPONSE` varchar(1024) DEFAULT NULL,
  `REPONSE` varchar(255) DEFAULT NULL,
  `VALEUR_MINUTE` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Structure de la table `demande_equipe`
--

DROP TABLE IF EXISTS `demande_equipe`;
CREATE TABLE `demande_equipe` (
  `ID_DEMANDE_EQUIPE` int(10) NOT NULL,
  `ID_COMPTE` int(10) NOT NULL,
  `ID_EQUIPE` int(10) NOT NULL,
  `POINT` int(10) NOT NULL DEFAULT '0',
  `STATUT_DEMANDE` int(10) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

DROP TABLE IF EXISTS `equipe`;
CREATE TABLE `equipe` (
  `ID_EQUIPE` int(10) NOT NULL,
  `NOM` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `inscription_defi`
--

DROP TABLE IF EXISTS `inscription_defi`;
CREATE TABLE `inscription_defi` (
  `ID_INSCRIPTION_DEFI` int(10) NOT NULL,
  `ID_COMPTE` int(10) NOT NULL,
  `ID_DEFI` int(10) DEFAULT NULL,
  `EST_REUSSI` int(10) DEFAULT '0',
  `VALEUR_MINUTE` int(11) DEFAULT '0',
  `DATE_INSCRIPTION` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
CREATE TABLE `lecture` (
  `ID_LECTURE` int(10) NOT NULL,
  `ID_COMPTE` int(10) NOT NULL,
  `TITRE` varchar(255) NOT NULL,
  `DATE_INSCRIPTION` datetime DEFAULT CURRENT_TIMESTAMP,
  `DUREE_MINUTES` int(10) NOT NULL,
  `EST_OBLIGATOIRE` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `config_site`
--

CREATE TABLE `config_site` (
  `ID_CONFIG` varchar(50) NOT NULL,
  `VALUE_CONFIG` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
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
-- Index pour la table `defi`
--
ALTER TABLE `defi`
  ADD PRIMARY KEY (`ID_DEFI`),
  ADD KEY `ID_COMPTE_FK` (`ID_COMPTE`);

--
-- Index pour la table `demande_equipe`
--
ALTER TABLE `demande_equipe`
  ADD PRIMARY KEY (`ID_DEMANDE_EQUIPE`),
  ADD KEY `ID_COMPTE` (`ID_COMPTE`),
  ADD KEY `ID_EQUIPE` (`ID_EQUIPE`);

--
-- Index pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD PRIMARY KEY (`ID_EQUIPE`),
  ADD UNIQUE KEY `NOM` (`NOM`);

--
-- Index pour la table `inscription_defi`
--
ALTER TABLE `inscription_defi`
  ADD PRIMARY KEY (`ID_INSCRIPTION_DEFI`),
  ADD KEY `ID_COMPTE` (`ID_COMPTE`),
  ADD KEY `ID_DEFI` (`ID_DEFI`);

--
-- Index pour la table `lecture`
--
ALTER TABLE `lecture`
  ADD PRIMARY KEY (`ID_LECTURE`),
  ADD KEY `ID_COMPTE` (`ID_COMPTE`);

--
-- Index pour la table `config_site`
--
ALTER TABLE `config_site`
  ADD PRIMARY KEY (`ID_CONFIG`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `ID_COMPTE` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `defi`
--
ALTER TABLE `defi`
  MODIFY `ID_DEFI` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `demande_equipe`
--
ALTER TABLE `demande_equipe`
  MODIFY `ID_DEMANDE_EQUIPE` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `equipe`
--
ALTER TABLE `equipe`
  MODIFY `ID_EQUIPE` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `inscription_defi`
--
ALTER TABLE `inscription_defi`
  MODIFY `ID_INSCRIPTION_DEFI` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `lecture`
--
ALTER TABLE `lecture`
  MODIFY `ID_LECTURE` int(10) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `COMPTE_FK1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`) ON DELETE SET NULL;

--
-- Contraintes pour la table `defi`
--
ALTER TABLE `defi`
  ADD CONSTRAINT `DEFI_FK1` FOREIGN KEY (`ID_COMPTE`) REFERENCES `compte` (`ID_COMPTE`) ON DELETE SET NULL;

--
-- Contraintes pour la table `demande_equipe`
--
ALTER TABLE `demande_equipe`
  ADD CONSTRAINT `DEMANDE_EQUIPE_FK1` FOREIGN KEY (`ID_COMPTE`) REFERENCES `compte` (`ID_COMPTE`) ON DELETE CASCADE,
  ADD CONSTRAINT `DEMANDE_EQUIPE_FK2` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`) ON DELETE CASCADE;

--
-- Contraintes pour la table `inscription_defi`
--
ALTER TABLE `inscription_defi`
  ADD CONSTRAINT `INSCRIPTION_DEFI_FK1` FOREIGN KEY (`ID_COMPTE`) REFERENCES `compte` (`ID_COMPTE`) ON DELETE CASCADE,
  ADD CONSTRAINT `INSCRIPTION_DEFI_FK2` FOREIGN KEY (`ID_DEFI`) REFERENCES `defi` (`ID_DEFI`) ON DELETE CASCADE;

--
-- Contraintes pour la table `lecture`
--
ALTER TABLE `lecture`
  ADD CONSTRAINT `LECTURE_FK1` FOREIGN KEY (`ID_COMPTE`) REFERENCES `compte` (`ID_COMPTE`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
