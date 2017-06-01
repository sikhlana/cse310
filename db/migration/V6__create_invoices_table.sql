CREATE TABLE `invoices` (
  `id`          INT(10)        NOT NULL AUTO_INCREMENT,
  `user_id`     INT(10)        NOT NULL,
  `order_id`    INT(10)        NOT NULL,
  `total_price` DECIMAL(10, 2) NOT NULL,
  `discount`    DECIMAL(10, 2) NOT NULL,
  `created_at`  TIMESTAMP      NOT NULL,
  `paid_at`     TIMESTAMP      NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;