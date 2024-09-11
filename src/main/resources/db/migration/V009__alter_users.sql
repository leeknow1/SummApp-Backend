ALTER TABLE `summapp`.`users`
    ADD COLUMN `creation_date` DATETIME NOT NULL DEFAULT current_timestamp AFTER `role_id`;
