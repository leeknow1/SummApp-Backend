CREATE TABLE `summapp`.`logs`
(
    `log_id`   INT      NOT NULL AUTO_INCREMENT,
    `log_date` DATETIME NOT NULL,
    `log_type` INT      NOT NULL,
    `log`      LONGTEXT NOT NULL,
    PRIMARY KEY (`log_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;
