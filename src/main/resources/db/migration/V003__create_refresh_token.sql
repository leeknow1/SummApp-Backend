CREATE TABLE `summapp`.`refresh_token`
(
    `refresh_token_id`            TEXT     NOT NULL,
    `user_id`       INT      NOT NULL,
    `token`         TEXT     NOT NULL,
    `creation_date` DATETIME NOT NULL,
    `finish_date`   DATETIME NOT NULL,
    INDEX `refresh_token_user_id_fkey_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `refresh_token_user_id_fkey`
        FOREIGN KEY (`user_id`)
            REFERENCES `summapp`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);