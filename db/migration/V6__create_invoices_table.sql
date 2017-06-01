CREATE TABLE `invoices` (
`id` INT NOT NULL AUTO_INCREMENT ,
`user_id` INT NOT NULL ,
`order_id` INT NOT NULL ,
`total_price` DECIMAL NOT NULL ,
`discount` DECIMAL NOT NULL ,
`created_at` TIMESTAMP NOT NULL ,
`paid_at` TIMESTAMP NOT NULL ,
PRIMARY KEY (`id`)
) ENGINE = InnoDB;