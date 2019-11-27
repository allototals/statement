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

ALTER TABLE `bank_statements` 
	CHANGE `account_type` `account_type` VARCHAR(30)  NOT NULL  DEFAULT '',
	CHANGE `currency_type` `currency_type` VARCHAR(3)  CHARACTER SET utf8  COLLATE utf8_general_ci  NOT NULL,  
	CHANGE `print_date` `print_date` DATETIME  NULL,
	CHANGE `start_statement_datetime` `start_statement_datetime` DATETIME  NULL,
	CHANGE `end_statement_datetime` `end_statement_datetime` DATETIME  NULL;

