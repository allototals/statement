/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl.exceptions;

/**
 *
 * @author michael
 */
public class PartnerBankStatementException extends BaseException {

    public PartnerBankStatementException() {
    }

    public PartnerBankStatementException(String message) {
        super(message);
    }

    public PartnerBankStatementException(Throwable cause) {
        super(cause);
    }

    public PartnerBankStatementException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public boolean isClientError() {
        return true;
    }
}
