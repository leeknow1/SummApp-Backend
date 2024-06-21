CREATE TABLE `summapp`.`events`
(
    `event_id`    INT      NOT NULL AUTO_INCREMENT,
    `event_time`  DATETIME NOT NULL,
    `type_id`     INT      NOT NULL,
    `description` LONGTEXT NOT NULL,
    PRIMARY KEY (`event_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;