CREATE TABLE `orders` (
  `id`           INT(10)        NOT NULL AUTO_INCREMENT,
  `user_id`      INT(10)        NOT NULL,
  `product_list` TEXT           NOT NULL,
  `created_at`   TIMESTAMP      NOT NULL,
  `total_amount` DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;