CREATE TABLE `summapp`.`users` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1;

INSERT INTO `summapp`.`users` (`username`, `password`, `email`) VALUES ('admin', '$2a$12$wx4XOcao17wa0rag0N8IoOHy.9Lh3i4QsHVmdNqocUsLO0F7hc9y.', 'admin@gmail.com');
INSERT INTO `summapp`.`users` (`username`, `password`, `email`) VALUES ('user', '$2a$12$XeoXZa1AokGFLa9Yw.JLYeREJM/gGZpQCVN1/15lqU2GceI0Cic46', 'user@gmail.com');
