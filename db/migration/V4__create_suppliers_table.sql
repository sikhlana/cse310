CREATE TABLE `suppliers` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `supplier_steet_1` VARCHAR(100) NOT NULL ,
    `supplier_street_2` VARCHAR(100) NOT NULL ,
    `supplier_city` VARCHAR(50) NOT NULL ,
    `supplier_state` VARCHAR(50) NOT NULL ,
    `supplier_zip` VARCHAR(10) NOT NULL ,
    `supplier_country` CHAR(2) NOT NULL ,
    `supplier_name` VARCHAR(100) NOT NULL ,
    `phone_number` VARCHAR(20) NOT NULL ,
    `email` VARCHAR(100) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;