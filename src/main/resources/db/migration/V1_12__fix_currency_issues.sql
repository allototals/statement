/* 
 * Copyright (C) One Finance & Investments Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by OneFi Developers <developers@onefi.co>, 2019
 */
/**
 * Author:  taiwo
 * Created: 07-Mar-2019
 */

ALTER TABLE `bank_statements` CHANGE `currency_type` `currency_type` VARCHAR(15)  CHARACTER SET utf8  COLLATE utf8_general_ci  NOT NULL  DEFAULT '';

RENAME TABLE `bank_statements_info` TO `bank_info`;
RENAME TABLE `bank_statements` TO `bank_statement`;
RENAME TABLE `bank_transactions` TO `bank_statement_transaction`;
