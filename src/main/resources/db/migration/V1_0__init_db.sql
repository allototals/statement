CREATE TABLE `bank_statements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `account_name` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `account_number` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `account_type` int(11) NOT NULL,
  `address` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `branch_address` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `branch_name` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `closing_balance` bigint(20) NOT NULL,
  `currency_type` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `end_statement_datetime` datetime NOT NULL,
  `opening_balance` bigint(20) NOT NULL,
  `page_count` int(11) NOT NULL,
  `print_date` datetime NOT NULL,
  `start_statement_datetime` datetime NOT NULL,
  `total_credit` int(11) NOT NULL,
  `total_credit_amount` bigint(20) NOT NULL,
  `total_debit` int(11) NOT NULL,
  `total_debit_amount` bigint(20) NOT NULL,
  `uncleared_balance` bigint(20) NOT NULL,
  `client_id` varchar(255) COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Table structure for table `bank_statements_info`
--

CREATE TABLE `bank_statements_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `help_link` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `upload_type` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Table structure for table `bank_transactions`
--


CREATE TABLE `bank_transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `amount` bigint(20) NOT NULL,
  `balance` bigint(20) NOT NULL,
  `previous_balance` bigint(20) NOT NULL,
  `reference_number` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `remarks` varchar(255) COLLATE utf8_general_ci DEFAULT NULL,
  `transaction_date` datetime NOT NULL,
  `transaction_type` varchar(255) COLLATE utf8_general_ci NOT NULL,
  `value_date` datetime NOT NULL,
  `statement_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2h7bxbvmmn79sy1tjxdkp2qi` (`statement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
