/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.services;

import co.onefi.bankstatement.models.PartnerBankStatementResponse;
import co.onefi.bankstatement.models.StatementDataRequest;
import java.util.List;

/**
 *
 * @author michael
 */
public interface PartnerBankStatmentService {

    List<PartnerBankStatementResponse> partnerBankStatement(StatementDataRequest statementDataRequest, String clientId);

}
