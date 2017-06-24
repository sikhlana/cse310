ALTER TABLE `orders`
  ADD COLUMN `status` ENUM('pending', 'completed', 'cancelled', 'returned') NOT NULL AFTER `user_id`;

ALTER TABLE `invoices`
  ADD COLUMN `status` ENUM('pending', 'paid', 'expired', 'refunded') NOT NULL AFTER `order_id`;

ALTER TABLE `rentals`
  ADD COLUMN `status` ENUM('pending', 'active', 'ended', 'expired') NOT NULL AFTER `product_id`;
