CREATE TABLE `summapp`.`users`
(
    `user_id`     INT          NOT NULL AUTO_INCREMENT,
    `first_name`  VARCHAR(255) NOT NULL,
    `last_name`   VARCHAR(255) NOT NULL,
    `middle_name` VARCHAR(255) NOT NULL,
    `username`    VARCHAR(255) NOT NULL,
    `password`    VARCHAR(255) NOT NULL,
    `email`       VARCHAR(255) NOT NULL,
    `role_id`     INT          NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `email_unique` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

INSERT INTO `summapp`.`users` (`first_name`, `last_name`, `middle_name`, `username`, `password`, `email`, `role_id`)
VALUES ('Админ', 'Админ', 'Админ', 'admin', '$2a$12$wx4XOcao17wa0rag0N8IoOHy.9Lh3i4QsHVmdNqocUsLO0F7hc9y.',
        'admin@gmail.com', 1);
INSERT INTO `summapp`.`users` (`first_name`, `last_name`, `middle_name`, `username`, `password`, `email`, `role_id`)
VALUES ('Пользователь', 'Пользователь', 'Пользователь', 'user',
        '$2a$12$XeoXZa1AokGFLa9Yw.JLYeREJM/gGZpQCVN1/15lqU2GceI0Cic46', 'user@gmail.com', 2);
INSERT INTO `summapp`.`users` (`first_name`, `last_name`, `middle_name`, `username`, `password`, `email`, `role_id`)
VALUES ('Сотрудник', 'Сотрудник', 'Сотрудник', 'employee',
        '$2a$12$rtSx/GM9lRoMs2xfLXM8ku6k3tscRn8TMbG9FPYRLWWrZtXaci8Lq', 'employee@gmail.com', 3);
