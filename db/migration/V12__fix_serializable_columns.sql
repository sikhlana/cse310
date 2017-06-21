ALTER TABLE `sessions`
  CHANGE COLUMN `data` `data` MEDIUMBLOB NULL DEFAULT NULL AFTER `token`;

ALTER TABLE `products`
  CHANGE COLUMN `meta` `meta` MEDIUMBLOB NULL DEFAULT NULL AFTER `rental_tier`;

ALTER TABLE `orders`
  CHANGE COLUMN `product_list` `product_list` MEDIUMBLOB NOT NULL AFTER `user_id`;

