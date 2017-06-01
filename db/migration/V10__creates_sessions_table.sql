CREATE TABLE `sessions` (
  `id`         INT(10)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `client_ip`  VARCHAR(16) NOT NULL,
  `hash`       CHAR(64)    NOT NULL,
  `token`      CHAR(64)    NOT NULL,
  `data`       TEXT,
  `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;