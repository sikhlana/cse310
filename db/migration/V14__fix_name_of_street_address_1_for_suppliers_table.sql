ALTER TABLE `suppliers`
  CHANGE COLUMN `address_steet_1` `address_street_1` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_unicode_ci' AFTER `name`;
