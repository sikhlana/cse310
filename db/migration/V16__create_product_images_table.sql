CREATE TABLE `product_images` (
  `id`             INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `product_id`     INT UNSIGNED NOT NULL,
  `file_hash`      CHAR(64)     NOT NULL,
  `file_size`      INT UNSIGNED NOT NULL,
  `file_extension` VARCHAR(10)  NOT NULL,
  `image_height`   INT UNSIGNED NOT NULL,
  `image_width`    INT UNSIGNED NOT NULL,
  `thumb_height`   INT UNSIGNED NOT NULL,
  `thumb_width`    INT UNSIGNED NOT NULL,
  INDEX `product_id` (`product_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;