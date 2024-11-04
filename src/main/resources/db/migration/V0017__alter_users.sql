ALTER TABLE `summapp`.`users`
    ADD COLUMN `enabled` TINYINT NOT NULL DEFAULT 0 AFTER `creation_date`,
    ADD COLUMN `activation_code` VARCHAR(6) NULL AFTER `enabled`;

UPDATE `summapp`.`users` SET users.enabled = 1 WHERE user_id IN (1,2,3);