-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 06. Jul 2018 um 11:39
-- Server-Version: 10.1.28-MariaDB
-- PHP-Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `jungschar`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `leiter`
--

CREATE TABLE `leiter` (
  `LeiterId` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Vorname` varchar(45) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `gruppe` varchar(45) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `leiter`
--

INSERT INTO `leiter` (`LeiterId`, `Name`, `Vorname`, `email`, `gruppe`, `position`) VALUES
(1, 'Gröber', 'Christian', 'christian.groeber@bluewin.ch', 'Waschbären', 'Gruppenleiter'),
(2, 'Schimmer', 'Simon', NULL, NULL, NULL),
(5, 'Schimmer', 'Lisa', NULL, NULL, NULL),
(6, 'Neukom', 'Nicola', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `programmpunkt`
--

CREATE TABLE `programmpunkt` (
  `ProgrammpunktId` int(11) NOT NULL,
  `VonH` int(11) NOT NULL,
  `VonM` int(11) NOT NULL,
  `BisH` int(11) NOT NULL,
  `BisM` int(11) NOT NULL,
  `Taetigkeit` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `programmpunkt2zustaendig`
--

CREATE TABLE `programmpunkt2zustaendig` (
  `ProgrammpunktId` int(11) NOT NULL,
  `LeiterId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `zustaendig`
--

CREATE TABLE `zustaendig` (
  `ZustaendigID` int(11) NOT NULL,
  `LeiterId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `leiter`
--
ALTER TABLE `leiter`
  ADD PRIMARY KEY (`LeiterId`);

--
-- Indizes für die Tabelle `programmpunkt`
--
ALTER TABLE `programmpunkt`
  ADD PRIMARY KEY (`ProgrammpunktId`);

--
-- Indizes für die Tabelle `zustaendig`
--
ALTER TABLE `zustaendig`
  ADD PRIMARY KEY (`ZustaendigID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `leiter`
--
ALTER TABLE `leiter`
  MODIFY `LeiterId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT für Tabelle `programmpunkt`
--
ALTER TABLE `programmpunkt`
  MODIFY `ProgrammpunktId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `zustaendig`
--
ALTER TABLE `zustaendig`
  MODIFY `ZustaendigID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
