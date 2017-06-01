CREATE TABLE `rentals` (
  `id`          INT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id`     INT       NOT NULL,
  `product_id`  INT       NOT NULL,
  `rented_at`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `returned_at` TIMESTAMP NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;