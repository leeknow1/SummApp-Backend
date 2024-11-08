ALTER TABLE `summapp`.`files`
    ADD COLUMN `application_id` INT NOT NULL AFTER `content`,
    ADD COLUMN `user_id`        INT NOT NULL AFTER `application_id`,
    ADD INDEX `files_application_id_fkey_idx` (`application_id` ASC) VISIBLE,
    ADD INDEX `files_user_id_fkey_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `summapp`.`files`
    ADD CONSTRAINT `files_application_id_fkey`
        FOREIGN KEY (`application_id`)
            REFERENCES `summapp`.`applications` (`application_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `files_user_id_fkey`
        FOREIGN KEY (`user_id`)
            REFERENCES `summapp`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;
