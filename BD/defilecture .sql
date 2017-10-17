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
  `ID_EQUIPE` int(10) NOT NULL,
  `NOM_EQUIPE` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lecture`
--

CREATE TABLE `lecture` (
  `ID_LECTURE` int(10) NOT NULL,
  `ID_PARTICIPANT` int(10) NOT NULL,
  `TITRE` varchar(255) NOT NULL,
  `DATE_INSCRIPTION` date NOT NULL,
  `DUREE_MINUTES` int(10) NOT NULL,
  `EST_OBLIGATOIRE` int(2) DEFAULT '0',
  `EST_DEFI` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `participant`
--

CREATE TABLE `participant` (
  `ID_PARTICIPANT` int(10) NOT NULL,
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
  `ROLE` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `equipe`
--
ALTER TABLE `equipe`
  ADD PRIMARY KEY (`ID_EQUIPE`),
  ADD UNIQUE KEY `NOM_EQUIPE` (`NOM_EQUIPE`);

--
-- Indexes for table `lecture`
--
ALTER TABLE `lecture`
  ADD PRIMARY KEY (`ID_LECTURE`),
  ADD KEY `ID_PARTICIPANT` (`ID_PARTICIPANT`);

--
-- Indexes for table `participant`
--
ALTER TABLE `participant`
  ADD PRIMARY KEY (`ID_PARTICIPANT`),
  ADD UNIQUE KEY `COURRIEL_UNQ` (`COURRIEL`),
  ADD UNIQUE KEY `PARTICIPANT_UNQ` (`PSEUDONYME`),
  ADD KEY `ID_EQUIPE` (`ID_EQUIPE`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `equipe`
--
ALTER TABLE `equipe`
  MODIFY `ID_EQUIPE` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `lecture`
--
ALTER TABLE `lecture`
  MODIFY `ID_LECTURE` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `participant`
--
ALTER TABLE `participant`
  MODIFY `ID_PARTICIPANT` int(10) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `lecture`
--
ALTER TABLE `lecture`
  ADD CONSTRAINT `LECTURE_FK` FOREIGN KEY (`ID_PARTICIPANT`) REFERENCES `participant` (`ID_PARTICIPANT`) ON DELETE CASCADE;

--
-- Constraints for table `participant`
--
ALTER TABLE `participant`
  ADD CONSTRAINT `PARTICIPANT_FK` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`) ON DELETE SET NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
