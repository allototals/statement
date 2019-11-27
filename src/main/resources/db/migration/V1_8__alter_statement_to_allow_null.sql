/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  michael
 * Created: 06-Mar-2019
 */

ALTER TABLE `bank_statements` 
CHANGE COLUMN `address` `address` VARCHAR(255) CHARACTER SET 'utf8' NULL ,
CHANGE COLUMN `branch_address` `branch_address` VARCHAR(255) CHARACTER SET 'utf8' NULL ,
CHANGE COLUMN `branch_name` `branch_name` VARCHAR(255) CHARACTER SET 'utf8' NULL ;