CREATE SCHEMA `weddingreservation` ;

CREATE TABLE `rsvp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `phone` varchar(8) NOT NULL,
  `confirmation_date` date NOT NULL,
  `comments` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
)