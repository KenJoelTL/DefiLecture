-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 15, 2017 at 10:30 AM
-- Server version: 5.7.11
-- PHP Version: 5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `defilecture`
--

-- --------------------------------------------------------

--
-- Table structure for table `equipe`
--

CREATE TABLE `equipe` (
  `idEquipe` int(10) NOT NULL,
  `nomEquipe` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lecture`
--

CREATE TABLE `lecture` (
  `idLecture` int(10) NOT NULL,
  `idParticipant` int(10) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `dateInscription` date NOT NULL,
  `dureeMinutes` int(10) NOT NULL,
  `obligatoire` int(2) DEFAULT '0',
  `defi` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `participant`
--

CREATE TABLE `participant` (
  `idParticipant` int(10) NOT NULL,
  `idEquipe` int(10) DEFAULT NULL,
  `nomUtilisateur` varchar(255) NOT NULL,
  `courriel` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `pointage` int(10) DEFAULT '0',
  `minutesRestantes` int(10) DEFAULT '0',
  `programmeEtude` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `pseudonyme` varchar(255) DEFAULT NULL,
  `role` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `equipe`
--
ALTER TABLE `equipe`
  ADD PRIMARY KEY (`idEquipe`),
  ADD UNIQUE KEY `nomEquipe` (`nomEquipe`);

--
-- Indexes for table `lecture`
--
ALTER TABLE `lecture`
  ADD PRIMARY KEY (`idLecture`),
  ADD KEY `idParticipant` (`idParticipant`);

--
-- Indexes for table `participant`
--
ALTER TABLE `participant`
  ADD PRIMARY KEY (`idParticipant`),
  ADD UNIQUE KEY `unique_courriel` (`courriel`),
  ADD UNIQUE KEY `participant_unq` (`nomUtilisateur`),
  ADD KEY `idEquipe` (`idEquipe`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `equipe`
--
ALTER TABLE `equipe`
  MODIFY `idEquipe` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `lecture`
--
ALTER TABLE `lecture`
  MODIFY `idLecture` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `participant`
--
ALTER TABLE `participant`
  MODIFY `idParticipant` int(10) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `lecture`
--
ALTER TABLE `lecture`
  ADD CONSTRAINT `lecture_fk` FOREIGN KEY (`idParticipant`) REFERENCES `participant` (`idParticipant`) ON DELETE CASCADE;

--
-- Constraints for table `participant`
--
ALTER TABLE `participant`
  ADD CONSTRAINT `participant_fk` FOREIGN KEY (`idEquipe`) REFERENCES `equipe` (`idEquipe`) ON DELETE SET NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
