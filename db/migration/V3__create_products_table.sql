CREATE TABLE `products` (
  `id`            INT(10)                                 NOT NULL AUTO_INCREMENT,
  `title`         VARCHAR(100)                            NOT NULL,
  `minimum_stock` INT(10)                                 NOT NULL DEFAULT '10',
  `stock`         INT(10)                                 NOT NULL,
  `sku`           CHAR(6)                                 NOT NULL,
  `type`          ENUM ('game', 'accessories', 'console') NOT NULL,
  `rental_tier`   INT(10)                                 NOT NULL DEFAULT '0',
  `meta`          TEXT,
  `supplier_id`   INT(10)                                 NOT NULL,
  `price`         DECIMAL(10, 2)                          NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;