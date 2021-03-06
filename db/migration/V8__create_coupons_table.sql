CREATE TABLE `coupons` (
  `id`              INT(10)        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `coupon_code`     VARCHAR(10)    NOT NULL UNIQUE,
  `user_id`         INT(10)        NOT NULL,
  `started_at`      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ended_at`        TIMESTAMP      NULL,
  `discount`        DECIMAL(10, 2) NOT NULL,
  `remaining_count` INT(10)        NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;