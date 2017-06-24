ALTER TABLE `products`
  ADD COLUMN `description` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci' AFTER `title`;
