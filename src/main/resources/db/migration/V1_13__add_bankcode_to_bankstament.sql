/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  michael
 * Created: 23-Apr-2019
 */

ALTER TABLE `bank_statement` 
ADD COLUMN `bank_code` VARCHAR(225) NULL AFTER `file_object_name`;