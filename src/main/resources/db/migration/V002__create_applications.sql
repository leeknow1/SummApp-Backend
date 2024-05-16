CREATE TABLE `summapp`.`applications`
(
    `application_id` INT      NOT NULL AUTO_INCREMENT,
    `number`         INT      NOT NULL,
    `user_id`        INT      NOT NULL,
    `creation_date`  DATETIME NOT NULL,
    `finish_date`    DATETIME NULL,
    `status_id`      INT      NOT NULL,
    PRIMARY KEY (`application_id`),
    UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,
    INDEX `applications_user_id_fkey_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `applications_user_id_fkey`
        FOREIGN KEY (`user_id`)
            REFERENCES `summapp`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;
