ALTER TABLE `bank_transactions` 
ADD CONSTRAINT `statement`
  FOREIGN KEY (`statement_id`)
  REFERENCES `bank_statements` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;