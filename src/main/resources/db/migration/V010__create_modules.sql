CREATE TABLE `summapp`.`modules`
(
    `module_id`   INT          NOT NULL AUTO_INCREMENT,
    `module_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`module_id`),
    UNIQUE INDEX `module_name_UNIQUE` (`module_name` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

INSERT INTO `summapp`.`modules` (`module_id`, `module_name`) VALUES ('1', 'applications');
