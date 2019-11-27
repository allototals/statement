/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl;

import co.onefi.bankstatement.entities.BankInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import co.onefi.bankstatement.repository.BankInfoRepository;
import co.onefi.bankstatement.services.BankInfoService;

/**
 *
 * @author michael
 */
@Service
@Slf4j
public class StatementsInfoServiceImpl implements BankInfoService {

    @Autowired
    private BankInfoRepository statementsInfoRepository;

    @Override
    public BankInfoEntity getStatementsInfoByBankCode(String bankCode) {
        return this.statementsInfoRepository.findFirstByBankCode(bankCode);
    }

}
