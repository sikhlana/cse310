ALTER TABLE `users`
  CHANGE COLUMN `billing_street_1` `billing_street_1` VARCHAR(100) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `phone_number`,
  CHANGE COLUMN `billing_street_2` `billing_street_2` VARCHAR(100) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `billing_street_1`,
  CHANGE COLUMN `billing_city` `billing_city` VARCHAR(50) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `billing_street_2`,
  CHANGE COLUMN `billing_state` `billing_state` VARCHAR(50) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `billing_city`,
  CHANGE COLUMN `billing_zip` `billing_zip` VARCHAR(10) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `billing_state`,
  CHANGE COLUMN `billing_country` `billing_country` CHAR(2) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `billing_zip`,
  CHANGE COLUMN `shipping_street_1` `shipping_street_1` VARCHAR(100) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `billing_country`,
  CHANGE COLUMN `shipping_street_2` `shipping_street_2` VARCHAR(100) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `shipping_street_1`,
  CHANGE COLUMN `shipping_city` `shipping_city` VARCHAR(50) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `shipping_street_2`,
  CHANGE COLUMN `shipping_state` `shipping_state` VARCHAR(50) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `shipping_city`,
  CHANGE COLUMN `shipping_zip` `shipping_zip` VARCHAR(10) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `shipping_state`,
  CHANGE COLUMN `shipping_country` `shipping_country` CHAR(2) NULL COLLATE 'utf8mb4_unicode_ci' AFTER `shipping_zip`,
  CHANGE COLUMN `purchase_point` `purchase_point` INT(10) NOT NULL DEFAULT '0' AFTER `shipping_country`;