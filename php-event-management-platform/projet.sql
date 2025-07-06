-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 10, 2022 at 09:09 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projet`
--

-- --------------------------------------------------------

--
-- Table structure for table `artist`
--

CREATE TABLE `artist` (
  `id` int(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `diffusion` varchar(255) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `picture` int(11) DEFAULT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `artist`
--

INSERT INTO `artist` (`id`, `name`, `contact`, `diffusion`, `categoryId`, `picture`, `description`) VALUES
(6, 'Rayzorks', 'florian.janier@hotmail.fr', 'https://soundcloud.com/rayzorks', 3, 200, 'Après un coup de foudre avec la musique psychédelique et de nombreux festivals, Florien se saisit des platines et travaille des dj sets psytrance allant de 150 à 155 BPM. Ses sélections se basent sur des labels tel que Sangoma, Squarelab ou encore Sonic Loom. Rayzork délivre des sets aux atmosphères à la fois lumineuse, entrainante et énergique avec une touche finale beaucoup plus ténébreuse,profonde, envoutante et mélancolique.'),
(7, 'PI.R2', 'https://www.facebook.com/Pi.r2Anomic', 'https://soundcloud.com/pi-r2?fbclid=IwAR3vGPIlKOv2Nif2vKK2lV8caO6l5iXu4izRZ6qPkcOIMgubcBDRDteD5Tw', 1, 201, 'π.r2 est un projet artistique né en début 2017 et membre du roster Anomic Elements. Pierre, longtemps passionné par la musique a découvert il y a quelques années l&#39;univers de la Psytrance, son intérêt croissant pour cet univers l&#39;a mené peu à peu à s&#39;intéresser au DJ set. Tout en restant ouvert aux différentes facettes de ce genre musical, le projet π.r² s&#39;oriente pour l&#39;instant vers une Psytrance dansante, aux sonorités darky voire parfois forest.\r\nToujours en recherche de nouveauté, Pierre aime mélanger les atmosphères des tracks afin de créer des histoires variées et parfois surprenantes. La narrativité est l&#39;axe principal de son travail, Pierre aime faire voguer le dancefloor entre puissance et légèreté grâce à des rythmiques endiablées bercées par des nappes allant de la mélancolie jusqu&#39;au plus profond des ténèbres.'),
(8, 'Psykosmoz', 'https://www.facebook.com/Psykosmoz/?ref=page_internal', 'https://www.facebook.com/Psykosmoz/?ref=page_internal', 6, 216, 'Décorations et scénographie en string art.');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `idCategory` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`idCategory`, `name`, `type`) VALUES
(1, 'music', 'psytrance'),
(2, 'music', 'livefunky psytrance'),
(3, 'music', 'darkspy'),
(4, 'music', 'hitech'),
(5, 'music', 'forest'),
(6, 'visual', 'decoration'),
(7, 'visual', 'graphisme\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `startDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `capacity` int(11) NOT NULL,
  `coverImage` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`id`, `name`, `description`, `startDate`, `endDate`, `capacity`, `coverImage`) VALUES
(38, 'ANOMIC DISTORSIONS #3', '▣ ADN et Anomic Elements s&#39;associent pour la troisième fois afin de vous proposer un événement d’exception au Ninkasi Kao. Anomic Distorsions, c&#39;est le souhait de mettre en avant une face plus underground de la psytrance à Lyon. A l&#39;occasion deux artistes internationaux viennent faire leur première à Lyon !\r\n▣ Après avoir invité Orestis, nous accueillerons du même label Sonic Loom Records TROMO. Son style ultra évolutif, entre forest et darkpsy mélodique, est un succès sur tous les dancefloors du monde. Jouant sur une gamme large de BPM, ses sets aux sonorités crystallines sont toujours parmis les plus reconnaissables.\r\n▣ A nos côtés, nous retrouverons également l&#39;autrichien KAIKKIALLA pour un live hitech à la fois minimaliste et mélodique bourré d&#39;émotion : prêt pour l&#39;envol ? Membre depuis peu du prestigieux label Kamino Records, il nous présentera de nombreuses unrealeased à l&#39;occasion de la sortie imminente de son album.\r\n▣ Ils seront accompagnés par plusieurs artistes montants de la scène française. En premier lieu, nous aurons le plaisir d&#39;accueillir THE HORRIDS signé chez Transubtil Records. Formé par Mogwaï et Shred&#39;er, ce duo développe une forest groovy des plus aiguisées, de quoi faire rougir les plus grands maîtres du genre. En second lieu, nous accueillerons TRIPSHIFT du label Multiversal Records pour représenter les couleurs psy groovy de la scène locale lyonnaise.\r\n▣ ADN Music sera notamment représentée par SYNEMA qui nous présentera son premier EP darkpsy mélodique EP façon ADN Cinematic sur la scène principale. MICROKOD, nouvelle recrue du label, viendra faire fondre le dancefloor avec sa dark progressive tout droit sortie du mordor pour notre side ADN Groove. Enfin, W.ECKO, entamera le de début de soirée de la scène du Club avec un dj set ethno grooves dont lui seul a le secret.\r\n▣ Anomic Elements sera représenté par PI.R² pour un dj set psytrance en ouverture de salle principale. RAYZORKS de cette même équipe Anomic, s&#39;occupera du closing du Club.\r\n▣ Enfin, à l&#39;occasion un décor adapté à la thématique sonore sera présenté par Fluofreax et VJ Kiwa ! Mapping, LED, structures diverses et variées, préparez vous à en prendre plein les yeux!\r\n▶ ▷ Prêt pour l&#39;aventure? ◀ ◁', '2021-10-22 21:55:00', '2022-01-11 05:00:00', 530, 217);

-- --------------------------------------------------------

--
-- Table structure for table `eventsImages`
--

CREATE TABLE `eventsImages` (
  `idImage` int(11) NOT NULL,
  `idEvent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `eventsImages`
--

INSERT INTO `eventsImages` (`idImage`, `idEvent`) VALUES
(204, 38),
(206, 38),
(207, 38),
(208, 38),
(209, 38),
(210, 38),
(211, 38),
(212, 38),
(213, 38),
(214, 38),
(215, 38),
(217, 38);

-- --------------------------------------------------------

--
-- Table structure for table `headline`
--

CREATE TABLE `headline` (
  `artistid` int(11) NOT NULL,
  `eventid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `id` int(100) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`id`, `name`) VALUES
(200, 'public/img/uploads/artists/27657059_741390192724310_2519598378353707351_n.png'),
(201, 'public/img/uploads/artists/avatars-VonJbWG3oEEdJWOy-qTWaYA-t500x500.jpg'),
(202, 'public/img/uploads/artists/52605850_351821689000713_3503407043845292032_n.jpg'),
(204, 'public/img/uploads/events/52605850_351821689000713_3503407043845292032_n.jpg'),
(205, 'public/img/uploads/events/AbstractImage.java'),
(206, 'public/img/uploads/events/252050453_1306902179813743_3365477532599281772_n.jpg'),
(207, 'public/img/uploads/events/252389335_1306902556480372_8130489978500338295_n.jpg'),
(208, 'public/img/uploads/events/252779293_1306902383147056_3955211642539730359_n.jpg'),
(209, 'public/img/uploads/events/252786092_1306902506480377_6751380620051210820_n.jpg'),
(210, 'public/img/uploads/events/252967312_1306902456480382_6241634517897660202_n.jpg'),
(211, 'public/img/uploads/events/252975681_1306902469813714_2882287158091784550_n.jpg'),
(212, 'public/img/uploads/events/253079430_1306902149813746_1088981692320256747_n.jpg'),
(213, 'public/img/uploads/events/253201329_1306902616480366_7319407343406661049_n.jpg'),
(214, 'public/img/uploads/events/253236857_1306902213147073_3237304875473497753_n.jpg'),
(215, 'public/img/uploads/events/253479753_1306902673147027_3545065745650844650_n.jpg'),
(216, 'public/img/uploads/artists/52605850_351821689000713_3503407043845292032_n.jpg'),
(217, 'public/img/uploads/events/avatars-VonJbWG3oEEdJWOy-qTWaYA-t500x500.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`) VALUES
(2, 'test', 'test@mail.com', '$2y$10$rKo1pxLhArZRM5uzqORvOuFW2PlzuqBLUEFcod7ACJwPoPJz22HKi'),
(3, 'test1', 'test2@mail.com', '$2y$10$lPA4ZjAC6w4P5PMxB4bAr.xyYvH5NMKqwakGvMHQWf6zYmNxYiY62'),
(4, 'test', 'test3@mail.com', '$2y$10$GA6EEoUwDaWDnt8qlArhMe/LdlUtzy6YekgE/Ukdc6a4fNWwim1VO');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `artist`
--
ALTER TABLE `artist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoryId` (`categoryId`),
  ADD KEY `picture` (`picture`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`idCategory`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `coverImage` (`coverImage`);

--
-- Indexes for table `eventsImages`
--
ALTER TABLE `eventsImages`
  ADD PRIMARY KEY (`idImage`,`idEvent`),
  ADD KEY `event FK` (`idEvent`);

--
-- Indexes for table `headline`
--
ALTER TABLE `headline`
  ADD PRIMARY KEY (`artistid`,`eventid`),
  ADD KEY `eventid` (`eventid`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `artist`
--
ALTER TABLE `artist`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `idCategory` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `images`
--
ALTER TABLE `images`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=218;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `artist`
--
ALTER TABLE `artist`
  ADD CONSTRAINT `artist_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `category` (`idCategory`),
  ADD CONSTRAINT `artist_ibfk_2` FOREIGN KEY (`picture`) REFERENCES `images` (`id`);

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `event_ibfk_1` FOREIGN KEY (`coverImage`) REFERENCES `images` (`id`);

--
-- Constraints for table `eventsImages`
--
ALTER TABLE `eventsImages`
  ADD CONSTRAINT `event FK` FOREIGN KEY (`idEvent`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `image FK` FOREIGN KEY (`idImage`) REFERENCES `images` (`id`);

--
-- Constraints for table `headline`
--
ALTER TABLE `headline`
  ADD CONSTRAINT `headline_ibfk_1` FOREIGN KEY (`artistid`) REFERENCES `artist` (`id`),
  ADD CONSTRAINT `headline_ibfk_2` FOREIGN KEY (`eventid`) REFERENCES `event` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
