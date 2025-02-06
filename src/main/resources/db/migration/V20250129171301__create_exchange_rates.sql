CREATE TABLE `summapp`.`exchange_rates`
(
    `usd` DOUBLE NOT NULL DEFAULT 0.00,
    `eur` DOUBLE NOT NULL DEFAULT 0.00,
    `rub` DOUBLE NOT NULL DEFAULT 0.00
);

INSERT INTO `summapp`.`exchange_rates`
VALUES (0.00, 0.00, 0.00);
