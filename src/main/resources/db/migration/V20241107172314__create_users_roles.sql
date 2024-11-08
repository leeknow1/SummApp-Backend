CREATE TABLE `summapp`.`users_roles`
(
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    INDEX `users_roles_user_id_fkey_idx` (`user_id` ASC) VISIBLE,
    INDEX `users_roles_role_id_fkey_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `users_roles_user_id_fkey`
        FOREIGN KEY (`user_id`)
            REFERENCES `summapp`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `users_roles_role_id_fkey`
        FOREIGN KEY (`role_id`)
            REFERENCES `summapp`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

INSERT INTO `summapp`.users_roles VALUES (1, 1);
INSERT INTO `summapp`.users_roles VALUES (2, 2);
INSERT INTO `summapp`.users_roles VALUES (3, 3);
