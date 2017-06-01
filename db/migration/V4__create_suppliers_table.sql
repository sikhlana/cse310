CREATE TABLE `suppliers` (
  `id`               INT(10)      NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(100) NOT NULL UNIQUE,
  `address_steet_1`  VARCHAR(100) NOT NULL,
  `address_street_2` VARCHAR(100) NOT NULL,
  `address_city`     VARCHAR(50)  NOT NULL,
  `address_state`    VARCHAR(50)  NOT NULL,
  `address_zip`      VARCHAR(10)  NOT NULL,
  `address_country`  CHAR(2)      NOT NULL,
  `phone_number`     VARCHAR(20)  NOT NULL,
  `email`            VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;