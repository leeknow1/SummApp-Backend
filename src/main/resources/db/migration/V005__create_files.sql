CREATE TABLE `summapp`.`files`
(
    `file_id`   INT      NOT NULL AUTO_INCREMENT,
    `file_name` TEXT     NOT NULL,
    `file_size` BIGINT   NOT NULL,
    `file_type` TEXT     NOT NULL,
    `content`   LONGBLOB NOT NULL,
    PRIMARY KEY (`file_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;
