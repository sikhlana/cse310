CREATE TABLE `customers` (
  `user_id` int(11) NOT NULL,
  `billing_street_1` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `billing_street_2` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `billing_city` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `billing_state` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `billing_zip` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `billing_country` char(2) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shipping_street_1` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shipping_street_2` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shipping_city` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shipping_state` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shipping_zip` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shipping_country` char(2) COLLATE utf8mb4_unicode_ci NOT NULL,
  `purchase_point` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;