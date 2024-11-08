CREATE TABLE `summapp`.`roles`
(
    `role_id`   INT          NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(250) NOT NULL,
    PRIMARY KEY (`role_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

INSERT INTO `summapp`.`roles` (`role_id`, `role_name`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `summapp`.`roles` (`role_id`, `role_name`) VALUES ('2', 'ROLE_USER');
INSERT INTO `summapp`.`roles` (`role_id`, `role_name`) VALUES ('3', 'ROLE_EMPLOYEE');
