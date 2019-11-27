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

ALTER TABLE `bank_transactions` CHANGE `reference_number` `reference_number` VARCHAR(255)  CHARACTER SET utf8  COLLATE utf8_general_ci  NULL  DEFAULT '';
