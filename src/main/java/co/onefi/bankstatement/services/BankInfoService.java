/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.services;

import co.onefi.bankstatement.entities.BankInfoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author michael
 */
public interface BankInfoService {

    // Method to get bank information by bank code.
    BankInfoEntity getStatementsInfoByBankCode(String bankCode);

}
