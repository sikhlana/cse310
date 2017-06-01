CREATE TABLE `products` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(100) NOT NULL ,
    `minimum_stock` INT NOT NULL ,
    `stock` INT NOT NULL ,
    `product_sku` CHAR(6) NOT NULL ,
    `product_type` ENUM('game','accessories','console') NOT NULL ,
    `rental_tier` INT NOT NULL ,
    `product_meta` TEXT ,
    `supplier_id` INT NOT NULL ,
    `price` DECIMAL(10, 2) NOT NULL,
     PRIMARY KEY (`id`)
 ) ENGINE = InnoDB;