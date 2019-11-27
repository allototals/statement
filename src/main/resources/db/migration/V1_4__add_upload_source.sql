/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  michael
 * Created: 28-Feb-2019
 */
ALTER TABLE `bank_statements` 
ADD COLUMN `source` VARCHAR(225) NULL AFTER `filehash`;
