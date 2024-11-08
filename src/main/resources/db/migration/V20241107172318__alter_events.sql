ALTER TABLE `summapp`.`events`
    CHANGE COLUMN `description` `description_ru` LONGTEXT NOT NULL,
    ADD COLUMN `description_kz` LONGTEXT NOT NULL AFTER `description_ru`;
