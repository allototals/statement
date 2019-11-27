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
ADD INDEX `clientId` (`client_id` ASC),
ADD UNIQUE INDEX `fileHash` (`filehash` ASC),
ADD INDEX `start_endDate` (`end_statement_datetime` ASC, `start_statement_datetime` ASC);
