ALTER TABLE `users`
  ADD COLUMN `billing_street_1` VARCHAR(100) NOT NULL,
  ADD COLUMN `billing_street_2` VARCHAR(100) NOT NULL,
  ADD COLUMN `billing_city` VARCHAR(50) NOT NULL,
  ADD COLUMN `billing_state` VARCHAR(50) NOT NULL,
  ADD COLUMN `billing_zip` VARCHAR(10) NOT NULL,
  ADD COLUMN `billing_country` CHAR(2) NOT NULL,
  ADD COLUMN `shipping_street_1` VARCHAR(100) NOT NULL,
  ADD COLUMN `shipping_street_2` VARCHAR(100) NOT NULL,
  ADD COLUMN `shipping_city` VARCHAR(50) NOT NULL,
  ADD COLUMN `shipping_state` VARCHAR(50) NOT NULL,
  ADD COLUMN `shipping_zip` VARCHAR(10) NOT NULL,
  ADD COLUMN `shipping_country` CHAR(2) NOT NULL,
  ADD COLUMN `purchase_point` INT(10) NOT NULL;