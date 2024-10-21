CREATE TABLE `summapp`.`modules_roles`
(
    `module_id`  INT NOT NULL,
    `role_id`    INT NOT NULL,
    `role_right` INT NOT NULL,
    INDEX `modules_roles_module_id_fkey_idx` (`module_id` ASC) VISIBLE,
    INDEX `modules_roles_user_id_fkey_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `modules_roles_module_id_fkey`
        FOREIGN KEY (`module_id`)
            REFERENCES `summapp`.`modules` (`module_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `modules_roles_user_id_fkey`
        FOREIGN KEY (`role_id`)
            REFERENCES `summapp`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

INSERT INTO `summapp`.`modules_roles` (`module_id`, `role_id`, `role_right`) VALUES (1, 1, 2);
INSERT INTO `summapp`.`modules_roles` (`module_id`, `role_id`, `role_right`) VALUES (1, 2, 2);
INSERT INTO `summapp`.`modules_roles` (`module_id`, `role_id`, `role_right`) VALUES (1, 3, 2);

