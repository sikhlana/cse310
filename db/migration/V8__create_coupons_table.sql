CREATE TABLE `coupons` (
`id` INT NOT NULL AUTO_INCREMENT ,
`coupon_code` VARCHAR(10) NOT NULL ,
`user_id` INT NOT NULL ,
`start_date` TIMESTAMP NOT NULL ,
`end_date` TIMESTAMP NOT NULL ,
`discount` DECIMAL(10, 2) NOT NULL ,
`remaining_count` INT NOT NULL ,
PRIMARY KEY (`id`),
UNIQUE (`coupon_code`)
) ENGINE = InnoDB;