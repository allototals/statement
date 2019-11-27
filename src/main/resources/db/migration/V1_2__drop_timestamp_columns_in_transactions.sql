/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  michael
 * Created: 27-Feb-2019
 */
ALTER TABLE `bank_transactions` 
DROP COLUMN `previous_balance`,
DROP COLUMN `updated_at`,
DROP COLUMN `created_at`;
