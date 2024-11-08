CREATE TABLE `summapp`.`schedules`
(
    `schedule_id`     INT          NOT NULL AUTO_INCREMENT,
    `schedule_name`   VARCHAR(255) NOT NULL,
    `enable`          TINYINT      NOT NULL DEFAULT 0,
    `cron_expression` VARCHAR(45)  NOT NULL DEFAULT '0 0 0 1 * *',
    PRIMARY KEY (`schedule_id`),
    UNIQUE INDEX `schedule_name_UNIQUE` (`schedule_name` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;
