CREATE TABLE `rentals` (
`id` INT NOT NULL AUTO_INCREMENT ,
`user_id` INT NOT NULL ,
`product_id` INT NOT NULL ,
`rental_date` TIMESTAMP NOT NULL ,
`return_date` TIMESTAMP NOT NULL ,
PRIMARY KEY (`id`)
) ENGINE = InnoDB;