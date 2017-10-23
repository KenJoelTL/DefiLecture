-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Sam 21 Octobre 2017 à 12:41
-- Version du serveur :  5.7.11
-- Version de PHP :  5.6.18
-- Créée par : Charles-André Fortin

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

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS demande_equipe;
DROP TABLE IF EXISTS inscription_defi;
DROP TABLE IF EXISTS defi;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS equipe;
DROP TABLE IF EXISTS compte;

SET FOREIGN_KEY_CHECKS=1;

--
-- Structure de la table `defi`
--

CREATE TABLE `defi` (
  `ID_DEFI` int(10) NOT NULL,
  `ID_COMPTE` int(10) DEFAULT NULL,
  `NOM_DEFI` varchar(255) NOT NULL,
  `DATE_DEBUT` varchar(255) NOT NULL,
  `DATE_FIN` varchar(255) NOT NULL,
  `QUESTION` varchar(255) DEFAULT NULL,
  `REPONSE` varchar(255) DEFAULT NULL,
  `POINT_DEFI` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `demande_equipe`
--

CREATE TABLE `demande_equipe` (
  `ID_DEMANDE_EQUIPE` int(10) NOT NULL,
  `ID_COMPTE` int(10) NOT NULL,
  `ID_EQUIPE` int(10) NOT NULL,
  `STATUT_DEMANDE` int(10) NOT NULL DEFAULT '0' /*0=en attente; 1=acceptée; 2=refusée*/
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE `equipe` (
  `ID_EQUIPE` int(10) NOT NULL,
  `NOM_EQUIPE` varchar(255) NOT NULL,
  `ID_CAPITAINE` int(11) DEFAULT NULL,
  `POINT_EQUIPE` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `inscription_defi`
--

CREATE TABLE `inscription_defi` (
  `ID_INSCRIPTION_DEFI` int(10) NOT NULL,
  `ID_COMPTE` int(10) NOT NULL,
  `ID_DEFI` int(10) DEFAULT NULL,
  `EST_REUSSI` int(10) DEFAULT NULL, /*0=non réussi; 1=réussi*/
  `DATE_INSCRIPTION_DEFI` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `lecture`
--

CREATE TABLE `lecture` (
  `ID_LECTURE` int(10) NOT NULL,
  `ID_COMPTE` int(10) NOT NULL,
  `TITRE` varchar(255) NOT NULL,
  `DATE_INSCRIPTION` datetime DEFAULT CURRENT_TIMESTAMP,
  `DUREE_MINUTES` int(10) NOT NULL,
  `EST_OBLIGATOIRE` int(2) DEFAULT '0' /*0=lecture non obligatoire; 1=lecture obligatoire*/
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `ID_COMPTE` int(10) NOT NULL,
  `ID_EQUIPE` int(10) DEFAULT NULL,
  `COURRIEL` varchar(255) NOT NULL,
  `MOT_PASSE` varchar(12) NOT NULL,
  `NOM` varchar(255) NOT NULL,
  `PRENOM` varchar(255) NOT NULL,
  `POINTAGE` int(10) DEFAULT '0',
  `MINUTES_RESTANTES` int(10) DEFAULT '0',
  `PROGRAMME_ETUDE` varchar(255) DEFAULT NULL,
  `AVATAR` varchar(255) DEFAULT NULL,
  `PSEUDONYME` varchar(255) DEFAULT NULL,
  `ROLE` int(10) NOT NULL DEFAULT '0' /*0=participant; 1=capitaine; 2=modérateur; 3=administrateur*/
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Index pour les tables exportées
--

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
  ADD UNIQUE KEY `NOM_EQUIPE` (`NOM_EQUIPE`),
  ADD KEY `ID_CAPITAINE` (`ID_CAPITAINE`);

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
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `ID_COMPTE` int(10) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

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
-- Contraintes pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD CONSTRAINT `EQUIPE_FK1` FOREIGN KEY (`ID_CAPITAINE`) REFERENCES `compte` (`ID_COMPTE`) ON DELETE SET NULL;

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

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `COMPTE_FK1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`) ON DELETE SET NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;