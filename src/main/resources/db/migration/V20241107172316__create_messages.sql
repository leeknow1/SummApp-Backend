CREATE TABLE `summapp`.`messages`
(
    `message_id` INT      NOT NULL AUTO_INCREMENT,
    `name_ru`     LONGTEXT NOT NULL,
    `name_kz`     LONGTEXT NOT NULL,
    PRIMARY KEY (`message_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;
