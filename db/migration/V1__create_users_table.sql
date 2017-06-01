CREATE TABLE `users` (
  `id`             INT(10)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`           VARCHAR(200) NOT NULL,
  `email`          VARCHAR(150) NOT NULL UNIQUE,
  `password`       CHAR(64)     NOT NULL,
  `is_staff`       TINYINT(1)   NOT NULL DEFAULT '0',
  `remember_token` CHAR(64)              DEFAULT NULL,
  `created_at`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `phone_number`   VARCHAR(20)  NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;