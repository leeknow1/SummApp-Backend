CREATE TABLE `summapp`.`modules_users`
(
    `module_id`  INT NOT NULL,
    `user_id`    INT NOT NULL,
    `user_right` INT NOT NULL,
    INDEX `modules_users_module_id_fkey_idx` (`module_id` ASC) VISIBLE,
    INDEX `modules_users_user_id_fkey_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `modules_users_module_id_fkey`
        FOREIGN KEY (`module_id`)
            REFERENCES `summapp`.`modules` (`module_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `modules_users_user_id_fkey`
        FOREIGN KEY (`user_id`)
            REFERENCES `summapp`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

INSERT INTO `summapp`.`modules_users` (`module_id`, `user_id`, `user_right`) VALUES ('1', '1', '2'), ('1', '2', '2');
