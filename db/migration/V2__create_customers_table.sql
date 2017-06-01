CREATE TABLE `customers` (
  `user_id`           INT(10)      NOT NULL PRIMARY KEY,
  `billing_street_1`  VARCHAR(100) NOT NULL,
  `billing_street_2`  VARCHAR(100) NOT NULL,
  `billing_city`      VARCHAR(50)  NOT NULL,
  `billing_state`     VARCHAR(50)  NOT NULL,
  `billing_zip`       VARCHAR(10)  NOT NULL,
  `billing_country`   CHAR(2)      NOT NULL,
  `shipping_street_1` VARCHAR(100) NOT NULL,
  `shipping_street_2` VARCHAR(100) NOT NULL,
  `shipping_city`     VARCHAR(50)  NOT NULL,
  `shipping_state`    VARCHAR(50)  NOT NULL,
  `shipping_zip`      VARCHAR(10)  NOT NULL,
  `shipping_country`  CHAR(2)      NOT NULL,
  `purchase_point`    INT(10)      NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;