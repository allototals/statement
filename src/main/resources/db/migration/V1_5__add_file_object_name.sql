/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  michael
 * Created: 01-Mar-2019
 */
ALTER TABLE `bank_statements` 
ADD COLUMN `file_object_name` VARCHAR(225) NOT NULL AFTER `source`;
